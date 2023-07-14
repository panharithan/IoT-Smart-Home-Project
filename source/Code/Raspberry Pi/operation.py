import socket
import sys
import time
import threading
from multiprocessing.dummy import Pool as ThreadPool
import bluetooth
import time
import sys
import RPi.GPIO as GPIO
from twilio.rest import Client
###################################
import cv2
from flask import Flask, render_template, Response
from camera import VideoCamera
import threading

email_update_interval = 600 # sends an email only once in this time interval
video_camera = VideoCamera(flip=True) # creates a camera object, flip vertically
object_classifier = cv2.CascadeClassifier("models/fullbody_recognition_model.xml") # an opencv classifier

# App Globals (do not edit)
app = Flask(__name__)
last_epoch = 0


@app.route('/')
def index():
    return render_template('index.php')

def gen(camera):
    while True:
        frame = camera.get_frame()
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen(video_camera),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

def OpenCamera():
    try:
        app.run(host='0.0.0.0', debug=False, threaded=True)
    except RuntimeError, msg:
        if str(msg) == "Server going down":
            pass  # or whatever you want to do when the server goes down
        else:
            print('Unexpected error!');
            # appropriate handling/logging of other runtime errors


###################################
BT2Sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(7, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)


def SendToOwner():
    account_sid = 'AC107e6a45220037385255373112534a8c'  # Found on Twilio Console Dashboard
    auth_token = '519058bea565f26099e2f7f7ae894c25'  # Found on Twilio Console Dashboard

    myPhone = '+85577410140'  # Phone number you used to verify your Twilio account
    # myPhone = '+85516833004' # Phone number you used to verify your Twilio account
    TwilioNumber = '+13023131509'  # Phone number given to you by Twilio

    client = Client(account_sid, auth_token)

    client.messages.create(
        to=myPhone,
        from_=TwilioNumber,
        body='Danger! House is on fire.')

def action(pin):
    print ('Gas detected !')
    SendToOwner()
    time.sleep(60)


def SmokeSensor():
        GPIO.add_event_detect(7, GPIO.RISING)
        GPIO.add_event_callback(7, action)
        try:
                while True:
                        time.sleep(0.5)
        except KeyboardInterrupt:
                GPIO.cleanup()
                sys.exit()


#-------------------------
def ReceivedFromBT(sock):
        msg=""
        data = sock.recv(1)
        while (data !="\n"):
                msg = msg + data
                data = sock.recv(1)
        msg1=msg
        return msg1

def ConnectBT1(Text):
        result="no result"
        bd_addr = "00:18:E4:34:F9:E1" #itade address
        sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
        port = 1
        try:
                sock.connect((bd_addr, port))
        except:
                print("Slave1 is down")
                isConnect_BT1=False
                delay(1)
                sys.close()
        isConnect_BT1=True
        print 'Connected To Slave 1'
        sock.send(Text)
        result = ReceivedFromBT(sock)
        sock.close()
        return result


def ConnectBT2(Text):
    result = ""
    bd_addr = "00:18:E4:34:F8:C9"  # itade address
    sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    port = 1
    try:
        sock.connect((bd_addr, port))
    except:
        print("Slave2 is down")
        isConnect_BT1 = False
        delay(1)
        sys.close()
    isConnect_BT1 = True
    print
    'Connected To Slave 2'
    sock.send(Text)
    result = ReceivedFromBT(sock)
    sock.close()
    return result


# --------------------------------------------------------------------------------
#--------------------------------------------------------------------------------
def SendToApp(Address , Text):
    try:
        print("Trying to send back to ", Address)
        s2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        port = 9999
        s2.connect((Address, port))
        s2.send(Text.encode())
        print(Text + " is sent")
    except IOError:
        print(IOError)
    s2.close()

def SendToApp2(Address , Text):
    try:
        print("Trying to send back to ", Address)
        s2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        port = 9999
        s2.connect((Address, port))
        s2.send(Text.encode())
        print(Text + " is sent")
    except IOError:
        print(IOError)
    s2.close()
#--------------------------------------------------------------------------------

def ServeClient( Conn, Addr):
    Angle=0


    while (True):

        try:
            print('connection from', Addr)
            # receive the data in small chunks and print it
            while True:
                command = Conn.recv(64)
                if command:
                    if (len(command) == 3): #work with Slave 1
                        SendToApp(Addr, ConnectBT1("g"))
                        break
                    elif (len(command) == 4):
                        SendToApp(Addr, ConnectBT1("g"))
                        break
                    elif (len(command) == 11):
                        SendToApp(Addr, ConnectBT1("e"))
                        break
                    elif (len(command) == 12):
                        SendToApp(Addr, ConnectBT1("f"))
                        break
                    elif (len(command) == 13):
                        SendToApp(Addr, ConnectBT1("d"))
                        break
                    elif (len(command) == 14):
                        SendToApp(Addr, ConnectBT1("c"))
                        break
                    #---- with Slave 2
                    elif (len(command) == 8):
                        SendToApp(Addr, ConnectBT2("z"))
                        break

                    elif (len(command) == 9):
                        SendToApp(Addr, ConnectBT2("y"))
                        break

                    else:
                        print("End of command")
                        break
                #SendToApp(Addr, "Wrong command detected")

          finally:
            x=0
            #Conn.close()
        #Soc.close()
#--------------------------------------------------------------------------------

def RunServer():
    HOST = '0.0.0.0'  # Symbolic name, meaning all available interfaces
    PORT = 8888  # Arbitrary non-privileged port
    HOST2 = ''
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('Socket created')
    # Bind socket to local host and port
    try:
        s.bind((HOST, PORT))
     except socket.error as msg:
            print
            ('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
            sys.exit()

        print('Socket bind complete')
        print('Local IP address: 192.168.0.11')
        print('Global IP address: 114.134.184.143')
        print('Listen To Port 8888')

        # Start listening on socket
        s.listen(10)
        print
        'Socket now listening'

        # now keep talking with the client
        while 1:
            # wait to accept a connection - blocking call
            conn, addr = s.accept()
            t = threading.Thread(target=ServeClient,args = (conn,addr[0],))
            t.start()
            t.join()
            #ServeClient(conn,addr[0])
            conn.close()

        s.close()

#--------------------------------------------------------------------------------
if (__name__ == '__main__'):
    while True:
                #thread for Smoke sensor
                t_smoke = threading.Thread(target=SmokeSensor)
                t_cam = threading.Thread(target=OpenCamera)


                t_smoke.start()
                t_cam.start()
                RunServer()
