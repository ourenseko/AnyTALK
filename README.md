*BAOFONGhz v. 0.1 (Alpha)*


Configuración principal de hardware
---
Conecta el microfono y parlante en los puertos de tu ordenador, activa el modo VOX 1 de tu emisora y sube el volumen al máximo.

Nota: En ordenadores portatiles puede ser necesario un adaptador para bifurcar microfono y altavoz



Instrucciones de Ejecución
---
Compilar los programas: Abre una terminal (o símbolo del sistema) y compila los archivos .java:

sh - Copy code
javac VoiceChatServer.java VoiceChatClient.java
Ejecutar el servidor: En la computadora que actuará como servidor, ejecuta:

sh - Copy code
java VoiceChatServer
Ejecutar el cliente: En la computadora que actuará como cliente, ejecuta:

sh - Copy code
java VoiceChatClient

Asegúrate de que el cliente tenga la dirección IP correcta del servidor. Puedes cambiar 127.0.0.1 en VoiceChatClient.java por la IP real del servidor.

Nota:
Ambos programas usan el puerto 12345. Asegúrate de que este puerto esté abierto en ambas computadoras y no esté bloqueado por ningún firewall.
Este ejemplo es bastante básico y no incluye manejo de errores robusto ni cifrado. Para un entorno de producción, se deben considerar más medidas de seguridad y gestión de errores.
La calidad de la transmisión puede variar dependiendo de la red y las capacidades de hardware de las computadoras involucradas.

