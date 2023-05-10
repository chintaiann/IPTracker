import random
def generate_random_ip(used_ips):
    # Generate a random IP address in the format xxx.xxx.xxx.xxx
    while True:
        ip = ".".join(str(random.randint(0, 255)) for _ in range(4))
        if ip not in used_ips:
            used_ips.add(ip)
            return ip
    
        

used_ips = set()
result = ""
count = 100
for i in range(count): 
    random_ip = generate_random_ip(used_ips) 
    if i == count-1: 
        result += random_ip
    else: 
        result += random_ip 
        result += ","

print(result)
print("\n") 

