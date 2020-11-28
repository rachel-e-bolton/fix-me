from simulation.instance import BrokerInstance
from random import randint


def buy_instrument_many_times(market, instrument, times=1, buy_min=100, buy_max=5000):
	broker = BrokerInstance()
	for i in range(times):
		amount = randint(1, 30)
		price = randint(buy_min, buy_max)
		result = broker.buy(market, instrument, amount, price)
		print(f"Buying {amount} {instrument} units from {market} at R{price}.00 per unit =>", result)

def sell_instrument_many_times(market, instrument, times=1, sell_min=1, sell_max=1000):
	broker = BrokerInstance()
	for i in range(times):
		amount = randint(1, 30)
		price = randint(sell_min, sell_max)
		result = broker.sell(market, instrument, amount, price)
		print(f"Selling {amount} {instrument} units to {market} for R{price}.00 per unit =>", result)

def order_unknown_market(market="Unknown"):
	broker = BrokerInstance()
	result = broker.buy(market, "Inst", 1, 1)
	print(f"Buying from {market}", result)
	result = broker.sell(market, "Inst", 1, 1)
	print(f"Selling to {market}", result)

def order_unknown_instrument(instrument="Unknown", market="Crypto"):
	broker = BrokerInstance()
	result = broker.buy(market, instrument, 1, 1)
	print(f"Buying {instrument} from {market}", result)
	result = broker.sell(market, instrument, 1, 1)
	print(f"Selling {instrument} to {market}", result)

def order_bad_amounts():
	broker = BrokerInstance()
	print(f"Buying -1 ETH from Crypto at R1", broker.buy("Crypto", "ETH", -1, 1))
	print(f"Selling -1 ETH to Crypto at R1", broker.sell("Crypto", "ETH", -1, 1))
	print(f"Buying 1 ETH from Crypto at R-1", broker.buy("Crypto", "ETH", 1, -1))
	print(f"Selling 1 ETH to Crypto at R-1", broker.sell("Crypto", "ETH", 1, -1))
	print(f"Buying 1000000000000 ETH from Crypto at R1", broker.buy("Crypto", "ETH", 1000000000000, 1))
	print(f"Buying 1 ETH from Crypto at R10000000000000.00", broker.buy("Crypto", "ETH", 1, 10000000000000) + " Because why not :)")

def send_anything(string):
	broker = BrokerInstance()
	print(f"Sending '{string}'", broker.send(string))