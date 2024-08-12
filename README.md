<h1>BAOFONGhz™</h1>

![baner-800px](https://github.com/ourenseko/BAOFONGhz/assets/25538565/2f8f2af3-63df-4168-862d-042b93d8f30c)



```Licencia de uso, not comercial purporses
BAOFONGhz ©️ COPYRIGHT 2024

(...) Cont'd file: BAOFONGhz/LICENSE

Permanet link: https://raw.githubusercontent.com/ourenseko/BAOFONGhz/main/LICENSE


```






Requisitos mínimos
---

-Un radiotransmisor FM ó DMR, para utilizar de estación

-Cables de audio y adaptadores, para conectar los jack en la radio

-Un dispositivo que ejecute mínimo Java 8

-Conexión a internet ó red local potente

-Conocimientos mínimos en redes

Versión 0.8.12082024 (Beta)
---
Descargar:  https://github.com/ourenseko/BAOFONGhz/blob/main/BAOFONGhz_v0.8.12082024.zip



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


Asegúrate de que el cliente tenga la dirección IP correcta del servidor. Puedes cambiar 127.0.0.1 en VoiceChatClient.java por la IP real del servidor. Para probar el sistema con una conexión a internet simplemente usar las IPs locales que asigna el router a cada dispositivo y abrir los puertos en el SO (Windows con seguridad avanzada)



Nota:
Ambos programas usan el puerto 12345. Asegúrate de que este puerto esté abierto en ambas computadoras y no esté bloqueado por ningún firewall.
Este ejemplo es bastante básico y no incluye manejo de errores robusto ni cifrado. Para un entorno de producción, se deben considerar más medidas de seguridad y gestión de errores.
La calidad de la transmisión puede variar dependiendo de la red y las capacidades de hardware de las computadoras involucradas.






Configuración principal de hardware y software 
---

En ordenadores portatiles y teléfonos (necesitas que pueda ejecutar Java) puede ser necesario un adaptador para bifurcar microfono y altavoz además de un reductor del jack 3.5mm a 2.5mm

Conecta el microfono y parlante en los puertos jack respectivamente, activa el modo VOX 1 de tu emisora y sube el volumen al máximo.

Sintoniza una frecuencia o canal silencioso y legal ó repetidor dedicado en el que va a operar la estación local

El servidor necesita tener una IP estática o usar un servicio noIP para mantener la conexión

En el firewall del router o teléfono (datos) redirecciona el puerto 1234 local al 8080 exterior (o asigna el que veas conveniente)

Nota: Es necesario que el IPS le provea de una IP única (variable ó fija) y que no forme parte de una red NAT para ahorrar matriculas IP, si no, la función servidor (transmitir) es muy probable que no funcione para tí al no poder identificar su computadora en la red. Solo podrás escuchar.



