<?php
// Configuración de la base de datos
$servername = "localhost"; // Cambia esto si tu servidor MySQL está en otro lugar
$username = "root"; // Cambia esto si tienes otro usuario
$password = ""; // Cambia esto si tienes otra contraseña
$dbname = "my_database"; // Nombre de la base de datos

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Obtener los parámetros de la URL
$ip64 = isset($_GET['ip']) ? $_GET['ip'] : '';
$mac64 = isset($_GET['mac']) ? $_GET['mac'] : '';
$os64 = isset($_GET['os']) ? $_GET['os'] : '';
$nav64 = isset($_GET['nav']) ? $_GET['nav'] : '';
$user64 = isset($_GET['user']) ? $_GET['user'] : '';
$typeLang64 = isset($_GET['typeLang']) ? $_GET['typeLang'] : '';
$timeReg64 = isset($_GET['timeReg']) ? $_GET['timeReg'] : '';

// Decodificar los parámetros
$ip = base64_decode($ip64);
$mac = base64_decode($mac64);
$os = base64_decode($os64);
$nav = base64_decode($nav64);
$user = base64_decode($user64);
$typeLang = base64_decode($typeLang64);
$timeReg = base64_decode($timeReg64);

// Obtener la IP pública del cliente
$ipPublic = $_SERVER['REMOTE_ADDR'];

// Preparar y ejecutar la consulta de inserción o actualización
$sql = "INSERT INTO system_data (ip, mac, os, nav, user, typeLang, timeReg, BAN, timeOnline)
        VALUES (?, ?, ?, ?, ?, ?, ?, FALSE, 0)
        ON DUPLICATE KEY UPDATE
            timeOnline = timeOnline + 1";

$stmt = $conn->prepare($sql);
$stmt->bind_param("sssssss", $ip, $mac, $os, $nav, $user, $typeLang, $timeReg);

if ($stmt->execute()) {
    // Comprobar si la IP pública coincide con el registro insertado/actualizado ---- editar esto, queremos que si esta ban que rechace la conexion. 
    $id = $conn->insert_id;
    $checkSql = "SELECT ip FROM system_data WHERE id = ?";
    $checkStmt = $conn->prepare($checkSql);
    $checkStmt->bind_param("i", $id);
    $checkStmt->execute();
    $result = $checkStmt->get_result();
    $row = $result->fetch_assoc();
    $storedIp = $row['ip'];

    if ($ipPublic === $storedIp) {
        echo "OK";
    } else {
        echo "Fail: IP pública no coincide";
    }
} else {
    echo "Fail: Error al insertar datos: " . $stmt->error;
}

// Cerrar la conexión
$stmt->close();
$conn->close();
?>
