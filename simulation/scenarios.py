from simulation.instance import BrokerInstance

from random import randint

from time import sleep

def buy_instrument_many_times(instrument, times=100):
	broker = BrokerInstance()

	for i in range(times):
		result = broker.buy("Crypto", instrument, 10, 5000)