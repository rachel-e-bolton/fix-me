from simulation.bootstrap import boot_environment, cleanup_environment
from simulation.scenarios import buy_instrument_many_times

from subprocess import Popen

import socket


if __name__ == "__main__":
    # boot_environment()

 

    buy_instrument_many_times("Crypto", "ETH")



    input("Pressing any key will kill the simulation")
    # cleanup_environment()