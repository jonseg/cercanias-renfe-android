# Aplicación Cercanías Renfe para dispositivos Android #

Este repositorio alberga el código fuente de la aplicación Cercanías Renfe para Android que fue suspendida a raiz de una denuncia de Renfe al departamento Android de Google, alegando que la aplicación incurría en competencia desleal al utilizar su logotipo.

La aplicación estuvo en el Android Market 2 años y contaba con 100.730 instalaciones totales y 51.896 instalaciones activas, siendo la aplicación más utilizada para consultar los horarios de Renfe en un dispositivo Android.

Si quieres saber más sobre este tema, puedes leerlo en mi blog:
*[Me han suspendido la aplicación de Cercanías Renfe para Android](http://jonsegador.com/2012/01/me-han-suspendido-la-aplicacion-de-cercanias-renfe-para-android/)*


## Sobre la aplicación ##

La aplicación se divide en dos partes:

## Parser ##
Se encuentra en la carpeta "parser". Es el código fuente que debe ir en nuestro servidor web y es el que "lee" la web de Renfe para extraer los horarios. La aplicación Android realiza llamadas a este script php para obtener los horarios.

Esta técnica es más conocida como "web scraping". Sería mucho más fácil que los datos estuvieran disponibles de forma libre a todo el mundo pero dudo que nadie en Renfe haya escuchado nunca nada sobre *[Open Data](http://en.wikipedia.org/wiki/Open_data)*.

## Aplicación Android ##
La encontraremos en la carpeta "android". Es el código fuente de la aplicación para Android. 

El código fuente se ha publicado "tal cual" según la última versión de la aplicación. Puede contener errores y el código se puede optimizar y mejorar con total seguridad. Echa un vistazo al archivo LICENSE.

El código no está muy comentado, de hecho, apenas hay comentarios y la documentación es básica. En próximas revisiones del código intentaré aclarar algunas partes del código o quizás haga un tutorial en mi blog explicando paso a paso el funcionamiento de la aplicación.

Básicamente debes saber que modificando el fichero HorariosActivity.java (línea 54) con la url donde hayas alojado el "parser" de la sección anterior, tienes más que suficiente para que la aplicación funcione.

~~~
// URL completa al script que parsea la web de renfe para obtener los horarios
String parser_url = "http://mipaginaweb.com/renfe/parser.php";   
~~~

Puedes descargarte directamente el fichero .apk e instalar la aplicación en tu dispositivo Android directamente.
*[Descargar fichero .apk de la aplicación Cercanías Renfe](http://jonsegador.com/cercanias/app/Renfe.apk)*


## Screenshots ##

[![Inicio](http://jonsegador.com/cercanias/screenshots/inicio_thumb.png)](http://jonsegador.com/cercanias/screenshots/inicio.png)
[![Horarios1](http://jonsegador.com/cercanias/screenshots/horarios1_thumb.png)](http://jonsegador.com/cercanias/screenshots/horarios1.png)
[![Horarios2](http://jonsegador.com/cercanias/screenshots/horarios2_thumb.png)](http://jonsegador.com/cercanias/screenshots/horarios2.png)


## Autor ##

* Jon Segador <jonseg@gmail.com>
* Twitter : *[@jonseg](http://twitter.com/#!/jonseg)*
* Linkedin: *[http://es.linkedin.com/pub/jon-segador/11/685/602](http://es.linkedin.com/pub/jon-segador/11/685/602)*
* Blog    : *[http://jonsegador.com/](http://jonsegador.com/)*
