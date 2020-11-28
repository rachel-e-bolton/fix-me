from simulation.messaging import checksum

import socket

class BrokerInstance():
	def __init__(self, host="127.0.0.1", port=5000):
		self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		try:
			self.sock.connect((host, port))
			self.logon()
		except Exception as e:
			raise

	def logon(self):
		data = self.sock.recv(1024)
		data = str(data.strip())
		self.id = data.split("109=")[-1].split("|")[0]


	def buy(self, market, instrument, amount, price):
		msg_base = "|".join([
			"35=B",
			f"109={self.id}",
			f"M={market}",
			f"I={instrument}",
			f"A={amount}",
			f"P={price}"
		]) + "|"
		message = msg_base + checksum(msg_base)
		self.sock.sendall(bytes(message + "\n", encoding='utf-8'))
		return self.get_response()


	def sell(self, market, instrument, amount, price):
		msg_base = "|".join([
			"35=S",
			f"109={self.id}",
			f"M={market}",
			f"I={instrument}",
			f"A={amount}",
			f"P={price}"
		]) + "|"
		message = msg_base + checksum(msg_base) 
		self.sock.sendall(bytes(message + "\n", encoding='utf-8'))
		return self.get_response()

	def send(self, message):
		self.sock.sendall(bytes(message + "\n", encoding='utf-8'))
		return self.get_response()

	def get_response(self):
		data = self.sock.recv(1024)
		data = str(data.strip())
		if "35=1" in data:
			return "\u001b[32mACCEPT\u001b[0m"
		elif "35=0" in data:
			return "\u001b[33mREJECT\u001b[0m : " + data.split("58=")[-1].split("|")[0]
		elif "35=3" in data and "58=" in data:
			return "\u001b[31mERROR\u001b[0m : " + data.split("58=")[-1].split("|")[0]
		else:
			return "\u001b[31mUNKNOWN ERROR\u001b[0m :( " + data