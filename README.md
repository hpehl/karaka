Karaka ([Maori for "clock"](http://maoridictionary.co.nz/index.cfm?dictionaryKeywords=clock)) is a personal time recording app deployed on Google AppEngine. Besides being useful I wanted to use the latest frameworks in a real application. Karaka uses the following stack:
* [Google App Engine](http://code.google.com/appengine/)
* [CDI (Weld)](http://seamframework.org/Weld) / [GIN](http://code.google.com/p/google-gin/)
* [Objectify](http://code.google.com/p/objectify-appengine/)
* [JAX-RS / RESTEasy](http://www.jboss.org/resteasy)
* [GWT 2.5](http://code.google.com/webtoolkit/) 
  * [Cell Widgets](http://code.google.com/webtoolkit/doc/latest/DevGuideUiCellWidgets.html)
  * [Editors](http://code.google.com/intl/de-DE/webtoolkit/doc/latest/DevGuideUiEditors.html)
  * [Logging](http://code.google.com/webtoolkit/doc/latest/DevGuideLogging.html)
* [GWTP](http://code.google.com/p/gwt-platform/)
* [Totoe](http://hpehl.github.com/totoe/)
* [Piriti](http://hpehl.github.com/piriti/)
* [GWT Highcharts](http://www.moxiegroup.com/moxieapps/gwt-highcharts/)

Work is still in progress. But you can take a first look at Karaka following the link below.
One last note: Karaka uses some of the new HTML5 / CSS3 features and therefore requires a modern browser. 

<a href="http://www.w3.org/html/logo/"><img src="http://www.w3.org/html/logo/badge/html5-badge-h-css3-graphics-semantics.png" width="197" height="64" title="HTML5 Powered with CSS3 / Styling, Graphics, 3D & Effects, and Semantics"/></a>

http://karaka-d8.appspot.com/

The deployed version is most likely behind the develop branch. Since Karaka uses maven it's easy to run the most recent version locally:
```
git clone https://github.com/hpehl/karaka.git
cd karaka
git checkout develop
mvn gae:unpack gae:run
```
Open http://localhost:8080/. The local version generates some sample time entries you can play with.