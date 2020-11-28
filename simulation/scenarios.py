from simulation.instance import BrokerInstance

from random import randint

from time import sleep

def buy_instrument_many_times(market, instrument, times=1):
	broker = BrokerInstance()

	for i in range(times):
		result = broker.buy(market, instrument, 2, 123)
		print(result)