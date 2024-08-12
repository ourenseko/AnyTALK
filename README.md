<h1>BAOFONGhz™</h1>

![baner-800px](https://github.com/ourenseko/BAOFONGhz/assets/25538565/2f8f2af3-63df-4168-862d-042b93d8f30c)


Requisitos mínimos
---

-Un radiotransmisor FM ó DMR, para utilizar de estación

-Cables de audio y adaptadores, para conectar los jack en la radio

-Un dispositivo que ejecute mínimo Java 8

-Conexión a internet ó red local potente

-Conocimientos mínimos en redes

Instrucciones de Ejecución v. 0.8.12082024 Beta
---
Descargar:  https://github.com/ourenseko/BAOFONGhz/blob/main/BAOFONGhz_v0.8.12082024.zip

![357082456-5291656a-7508-48f9-a837-c31018c665ab](https://github.com/user-attachments/assets/a92f4d86-e195-4b25-8638-fd5fb128761f)



Nuevas funciones (Necesita la bliblioteca "lib/gson-2.8.9.jar" y mantener la estuctura de directorios)

-Funcionalidad LORO repetidor: Repite la entrada de audio para que la emisora base emita a su alrededor.

-Funcionalidad Guardar emisora: Guarda la estación en un archivo apuntando la dirección del servidor remoto.

-Funcionalidad Emisoras: Muestra una tabla con las estaciones guardadas.





Instrucciones de Ejecución v. 0.6.13072024 Beta
---


![image](https://github.com/user-attachments/assets/1c54c7cc-5590-4186-8757-df7f8cddc90f)

-ó-

Server (SHELL)
```Java
java -jar BAOFONGhz.jar -servidor <LOCAL_PORT>
java -jar BAOFONGhz.jar -s 32323
```

Client (SHELL)
```Java
java -jar BAOFONGhz.jar -cliente <IP> <PORT>
java -jar BAOFONGhz.jar -c 8.8.8.8 80
```

Instrucciones de Ejecución v. 0.4.12072024 Beta
---

Descargar: https://github.com/ourenseko/BAOFONGhz/releases/tag/v.0.3.11072024

![v  0 4](https://github.com/ourenseko/BAOFONGhz/assets/25538565/380a0b2f-3e36-4820-9c4b-0ffe59d87592)

 
Instrucciones de Ejecución v. 0.2.10072024 Alpha (GUI)
---

UPDAPTED.

![image](https://github.com/ourenseko/BAOFONGhz/assets/25538565/db09cb01-b0ea-49e3-95cf-873e0fa551a8)



Instrucciones de Ejecución v. 0.1 Alpha (SHELL)
---
Compilar los programas: Abre una terminal (o símbolo del sistema) y compila los archivos .java:

```Java
 javac VoiceChatServer.java VoiceChatClient.java
```

Ejecutar el servidor: En la computadora que actuará como servidor

```Java 
 java VoiceChatServer
```

Ejecutar el cliente: En la computadora que actuará como cliente
```Java
 java VoiceChatClient
```

Asegúrate de que el cliente tenga la dirección IP correcta del servidor. Puedes cambiar 127.0.0.1 en VoiceChatClient.java por la IP real del servidor. Para probar el sistema con una conexión a internet simplemente usar las IPs locales que asigna el router a cada dispositivo y abrir los puertos en el SO (Windows con seguridad avanzada)



Nota:
Ambos programas usan el puerto 12345. Asegúrate de que este puerto esté abierto en ambas computadoras y no esté bloqueado por ningún firewall.
Este ejemplo es bastante básico y no incluye manejo de errores robusto ni cifrado. Para un entorno de producción, se deben considerar más medidas de seguridad y gestión de errores.
La calidad de la transmisión puede variar dependiendo de la red y las capacidades de hardware de las computadoras involucradas.




```Licencia de uso, not comercial purporses
BAOFENG ©️ COPYRIGHT 2024

(...) Cont'd file: BAOFONGhz/LICENSE

Permanet link: https://raw.githubusercontent.com/ourenseko/BAOFONGhz/main/LICENSE


```


Configuración principal de hardware y software 
---

En ordenadores portatiles y teléfonos (necesitas que pueda ejecutar Java) puede ser necesario un adaptador para bifurcar microfono y altavoz además de un reductor del jack 3.5mm a 2.5mm

Conecta el microfono y parlante en los puertos jack respectivamente, activa el modo VOX 1 de tu emisora y sube el volumen al máximo.

Sintoniza una frecuencia silenciosa (legal) o repetidor dedicado en el que va a operar la estación local

El servidor necesita tener una IP estática o usar un servicio noIP para mantener la conexión

En el firewall del router o teléfono (datos) redirecciona el puerto 1234 local al 8080 exterior (o asigna el que veas conveniente)

Nota: Es necesario que el IPS le provea de una IP única (variable ó fija) y que no forme parte de una red NAT para ahorrar matriculas IP, si no, la función servidor (transmitir) es muy probable que no funcione para tí al no poder identificar su computadora en la red. Solo podrás escuchar.


Recursos de interes
--

The Pi4J Project: Este proyecto tiene como objetivo proporcionar una API de Entrada/Salida orientada a objetos y bibliotecas de implementación para que los programadores de Java accedan a todas las capacidades de E/S de la plataforma Raspberry Pi.

https://github.com/Pi4J

https://github.com/Pi4J/pi4j-v2



