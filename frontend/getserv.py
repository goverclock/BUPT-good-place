#!env python3
env = open('.env')
serverAddr = env.read()
x = f"""// global config, do not add to git
export default {{
    serverAddr:
        'http://{serverAddr}:8080/'
}}
"""

f = open('src/config.js', 'w')
f.write(x)
f.close()
