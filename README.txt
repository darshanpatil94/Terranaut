-----------
# Terranaut
-----------
An Android application for wireless maneuvering of robots.

----------
JAVA FILES
----------
- Startup.java
- MainActivity.java
- BtConnectionThread.java
- About.java

1. Startup.java
----------------
  - Implemented to display the startup screen of application.
  - Startup class extends Activity class imported from android.app.Activity package.
  - Ovrerrides onCreate and onDestroy methods of Activity class.
  - startActivity method is used to create new intent imported from android.content.Intent

2. MainActivity.java
--------------------
  - The main screen and fuctionalities are included in the MainActivity Class.
        Functionalities: - Establish bluetooth connection with bot(HC-05)
                         - Buttons to move the bot forward, backward, and movement of ARM's.
                         - Changing the direction of the bot by tilting the android phone.
  - MainActivity Class extends Activity imported from import android.app.Activity package,
                      implements SensorEventListener imported from android.hardware.SensorEventListener package,
                      implements Handler.Callback imported from android.os.Handler package.
  - Implemented bluetooth functionalities by importing android.bluetooth.* package.
  - Read accelerometer sensor values using android.hardware.* package.
  - using android.view.* package.
  - Implemented functionalities for Button, Spinner, TextView, Toast using android.widget.* package.

3. BtConnectionThread.java
--------------------------
- Implemented to perform functionalities related to bluetooth.
- BtConnectionThread class extends Thread imported from java.lang.Thread.

4. About.java
-------------
- Implemented to display the developer information.


Reference:
https://developers.google.com/android/
