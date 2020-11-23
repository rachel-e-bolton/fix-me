import socket
from time import sleep

sending = [
    "35=A|1=Glen Wasserfall|",
    "35=3|109=B0000000001|",
    "35=3|109=B0000000001|"
]

def checksum(string):
    total = 0
    for i in list(string):
        total += 0xFF & ord(i)
    total = total % 256
    print(str(total).zfill(3))
    return "10={}|".format(str(total).zfill(3))

checksum("35=3|58=Error: Checksum is invalid|10=032|")

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.connect(('127.0.0.1', 5000))
    while True:
        s.sendall(bytes(sending[0] + checksum(sending[0]) + "\n", encoding='ascii'))
        data = s.recv(1024)
        print(data.strip())

        s.sendall(bytes(sending[1] + checksum(sending[1]) + "\n", encoding='ascii'))
        data = s.recv(1024)
        print(data.strip())
        input("Send?")
except KeyboardInterrupt:
    raise
except Exception as e:
    print(e)
    print("Sleeping for 1 second")
    sleep(1)
finally:
    s.close()