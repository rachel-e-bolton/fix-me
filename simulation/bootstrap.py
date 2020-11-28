from multiprocessing import Process
from subprocess import Popen
from time import sleep

def start_java_app(name):
    
	Popen(["java", "-jar", f"{name}/target/{name}-1.0-SNAPSHOT.jar"])

def cleanup_environment():
    Popen(["pkill", "java"])

def boot_environment():
	router = Process(target=start_java_app, args=("router",))
	market = Process(target=start_java_app, args=("market",))

	router.start()
	sleep(1)
	market.start()
	print("Waiting a few seconds for everything to start....")
	sleep(3)