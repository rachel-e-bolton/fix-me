from simulation.bootstrap import boot_environment, cleanup_environment
from simulation.scenarios import *

if __name__ == "__main__":
    # boot_environment()
 
    print("Simulating many buy requests")
    buy_instrument_many_times("Crypto", "ETH", 10)

    print("\n\nSimulating many sell requests")
    sell_instrument_many_times("Crypto", "ETH", 10)

    print("\n\nSimulating unknown market")
    order_unknown_market("SomeCrazyNameThatDoesntExist")

    print("\n\nSimulating unknown instrument")
    order_unknown_instrument("WhatIsThisThatIWantToBuy")

    print("\n\nSimulating bad amounts")
    order_bad_amounts()


    # cleanup_environment()