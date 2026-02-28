import pyautogui 
import time
import pandas

pyautogui.PAUSE = 0.5
link = "https://dlp.hashtagtreinamentos.com/python/intensivao/login"

# Abrir o navegador
pyautogui.press("win")
pyautogui.write("edge")

# Abrir o sistema da empresa
pyautogui.press("enter")
pyautogui.write(link)
pyautogui.press("enter")
time.sleep(3)

# Fazer login
pyautogui.click(x=905, y=468)
pyautogui.write("pythonimpressionador@gmail.com")
pyautogui.press("tab")
pyautogui.write("finalmente eu consegui hahaha")
pyautogui.press("tab")
pyautogui.press("enter")
time.sleep(3)

# Cadastrar produto
tabela = pandas.read_csv("produtos.csv")
print(tabela)

for linha in tabela.index:
    # codigo
    pyautogui.click(x=727, y=309) 
    codigo = tabela.loc[linha, "codigo"]
    pyautogui.write(str(codigo))
    # marca
    pyautogui.press("tab")
    marca = tabela.loc[linha, "marca"]
    pyautogui.write(str(marca))
    # tipo
    pyautogui.press("tab")
    tipo = tabela.loc[linha, "tipo"]
    pyautogui.write(str(tipo))
    # categoria
    pyautogui.press("tab")
    categoria = tabela.loc[linha, "categoria"]
    pyautogui.write(str(categoria))
    # preco
    pyautogui.press("tab")
    preco = tabela.loc[linha, "preco_unitario"]
    pyautogui.write(str(preco))
    # custo
    pyautogui.press("tab")
    custo = tabela.loc[linha ,"custo"]
    pyautogui.write(str(custo))
    # obs
    pyautogui.press("tab")
    obs = tabela.loc[linha, "obs"]
    pyautogui.write(str(obs))

    # Enviar produto
    pyautogui.press("tab")
    pyautogui.press("enter")
    pyautogui.scroll(5000)