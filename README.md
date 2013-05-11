# lambdajam
## Patterns in Types: A Look at Reader, Writer and State in Scala

This is the base project for "Pattern in Types" jam, for the LambdaJam
Australia conference.

__note__: please test your environment before you arrive so we can get
started quickly on the day.

__note__: test you environment as soon as possible, but please grab the
latest copy of this repository the day before the jam with a `git pull origin master`.


## Getting started

Before you attend you will need to get a few things
ready and ensure everything is setup properly. `sbt`
is going to do all the heavy lifting though, so
hopefully it is all straight forward, if not, send
us an email via <mark@hibberd.id.au>.


Pre requisuites.

 1. A valid install of java 6+
 2. git
 3. **if you are windows only** install sbt using the [msi installer](http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt/0.12.3/sbt.msi)


Getting scala and validating your environment (for unix):

    git clone https://github.com/markhibberd/lambdajam-patterns-in-types.git
    cd lambdajam-patterns-in-types
    ./sbt test


Getting scala and validating your environment (for windows):

    git clone https://github.com/markhibberd/lambdajam-patterns-in-types.git
    cd lambdajam-patterns-in-types
    sbt test


For either platform this may take a few minutes. It will:

 1. Download the sbt build tool.
 2. Download the required versions of scala.
 3. Compile the main and test source code.
 4. Run the tests.


You should see green output, no errors and, an exit code of 0.


## Working with scala.

Any good text editor will be sufficient for the course. If you
prefer an IDE, you can use the eclipse based scala-ide or
intellij with the scala and sbt plugins installed.

You can generate project files for intellij with:

    ./sbt 'gen-idea no-classifiers'

You can generate project files for eclipse with:

    ./sbt eclipse

Just note that if you choose eclipse or intellij, have a
backup texteditor as well, because there won't be enough
time to debug any editor issues.
