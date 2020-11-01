import socket
from time import sleep


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.connect(('127.0.0.1', 5000))
    while True:
        sending = input("What I send? : ") + "\n"
        print(sending)
        s.sendall(bytes(sending, encoding='utf8'))
        data = s.recv(1024)
        if data.strip() == b"abort":
            quit()
        print("Got back", data)
        sleep(1)
except KeyboardInterrupt:
    raise
except Exception as e:
    print(e)
    print("Sleeping for 1 second")
    sleep(1)
finally:
    s.close()