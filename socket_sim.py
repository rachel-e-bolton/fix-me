import socket
from time import sleep

sending = [
    "35=B|109=B0000000001|M=Crypto|I=ETH|A=1|P=5000.0|",
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

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.connect(('127.0.0.1', 5000))
    while True:
        # Get Broker ID first
        data = s.recv(1024)
        print(data.strip())

        # s.sendall(bytes(sending[1] + checksum(sending[1]) + "\n", encoding='ascii'))
        # data = s.recv(1024)
        # print(data.strip())
        input("Send?")
        s.sendall(bytes(sending[0] + checksum(sending[0]) + "\n", encoding='ascii'))
except KeyboardInterrupt:
    raise
except Exception as e:
    print(e)
    print("Sleeping for 1 second")
    sleep(1)
finally:
    s.close()