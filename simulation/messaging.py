def checksum(string):
    total = 0
    for i in list(string):
        total += 0xFF & ord(i)
    total = total % 256
    return "10={}|".format(str(total).zfill(3))