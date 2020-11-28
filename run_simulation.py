from simulation.bootstrap import boot_environment, cleanup_environment
from simulation.scenarios import *

if __name__ == "__main__":
    # boot_environment()
 
    print("\u001b[36m=== Simulating many buy requests ===\u001b[0m")
    
    buy_instrument_many_times("Crypto", "ETH", 10)

    print("\n\n\u001b[36m=== Simulating many sell requests ===\u001b[0m")
    
    sell_instrument_many_times("Crypto", "ETH", 10)

    print("\n\n\u001b[36m=== Simulating unknown market ===\u001b[0m")
    
    order_unknown_market("SomeCrazyNameThatDoesntExist")

    print("\n\n\u001b[36m=== Simulating unknown instrument ===\u001b[0m")

    order_unknown_instrument("WhatIsThisThatIWantToBuy ===")

    print("\n\n\u001b[36m=== Simulating bad amounts ==\u001b[0m")
    
    order_bad_amounts()


    # cleanup_environment()