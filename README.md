SeriesMover
===========

If you download a new season pack, and have some of the files run this app.

$ java -jar seriesmover.jar 'target' 'source'

It will find all series in target and see if it finds a similar file in source, if it finds a similar file it will give you the option to move it to 'target'.


Building from source
--------------------

	mvn clean install

Will build lib and app, and copy needed dependencies to seriesmover/target/lib
