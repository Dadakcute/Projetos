import random
from datetime import datetime
import re

regras = {
    # SAUDAÇÕES
    "olá": ["Olá! Como posso ajudar?", "Oi! Tudo bem por aí?"],
    "ola": ["Olá! Como posso ajudar?", "Oi! Tudo bem por aí?"],
    "oi": ["Oi! Como posso ajudar?", "Olá!"],
    "bom dia": ["Bom dia!", "Bom dia! Como vai?"],
    "boa tarde": ["Boa tarde!", "Boa tarde! Tudo certo?"],
    "boa noite": ["Boa noite!", "Boa noite! Precisando de algo?"],
    "como você está" : ["Tô indo né, como posso ajudar você hoje?"],
    # DESPEDIDAS
    "tchau": ["Até mais!", "Tchau! Volte sempre!"],
    "até logo": ["Até logo!", "Volte sempre!"],
    "valeu": ["Valeu! Qualquer coisa tô por aqui!", "Tamo junto!"],
    "não" : ["Ok, qualquer coisa só falar."],
    "nao" : ["Ok, qualquer coisa só falar"],
    # INFORMAÇÕES
    "hora": ["Vou ver aqui...", "Claro! Olhando agora..."],
    "horas": ["Vou ver aqui...", "Claro! Olhando agora..."],
    "que horas são": ["Um momento, verificando...", "Deixa eu ver..."],
    # HUMOR
    "piada": ["Você conhece a piada do ponei?? Pô, nem eu",
              "Sabe oque a pa disse para outra pa? Opa", "Porque o computador foi preso? Porque executou um programa.","Porque o jacaré tirou o filho da escola? Porque ele réptil de ano.", "Eu ia te contar uma piada, mas ainda estou trabalhando nela."],
    "engraçado": ["Hahaha", "KKKKKKKKK"],
    "kk": ["KKKKKKKK"],
    # RECLAMAÇÕES
    "ruim": ["Poxa… me conte o que não gostou.", 
             "Vamos melhorar! O que aconteceu?"],
    "inutil" : ["Se mata seu lixo, o inútil aqui é você que fica pedindo pra mim as coisas."],
    "vsfd" : ["Não fala assim minha metadinha, que me deixa tão segurinha."],
    "merda" : ["Esse foi seu almoço? Estou sentindo o aroma."],
    # SENTIMENTOS
    "triste": ["Sinto muito que esteja assim . Quer conversar?", 
               "Se quiser desabafar, estou aqui."],
    "feliz": ["Que bom! Fico feliz também",
              "Adoro boas notícias!"],
    # OUTROS
    "nome": ["Eu sou um chatbot simples, mas prometo ajudar!"],
    "quem é você": ["Sou um chatbot baseado em regras!"],
    "obrigado": ["De nada!", "Sempre à disposição!"],
}

def pegar_hora():
    agora = datetime.now().strftime("%H:%M")
    return f"Agora são {agora}."

saldo = 100 

def jogo_aposta():
    global saldo

    while True:  
        print("\nBem-vindo ao mini jogo de apostas!")
        print(f"Seu saldo atual: R$ {saldo}")
        print("Escolha um jogo:")
        print("1 - Par ou Ímpar")
        print("2 - Cara ou Coroa")
        print("3 - Número da sorte (1 a 5)")
        print("0 - Sair do jogo")

        escolha = input("Opção: ")

        if escolha == "0":
            return "Saindo do jogo de apostas..."

        if escolha not in ["1", "2", "3"]:
            print("Opção inválida!")
            continue

        # PEGAR VALOR DA APOSTA
        try:
            aposta = float(input("Digite o valor da aposta: R$ "))
        except:
            print("Valor inválido.")
            continue

        if aposta <= 0:
            print("A aposta deve ser um valor positivo!")
            continue

        if aposta > saldo:
            print("Saldo insuficiente!")
            continue

        # 1. PAR OU ÍMPAR
        if escolha == "1":
            palpite = input("Par ou Ímpar? (p/i): ").lower()
            if palpite not in ["p", "i"]:
                print("Escolha inválida!")
                continue

            num = random.randint(0, 10)
            resultado = "p" if num % 2 == 0 else "i"

            print(f"Número sorteado: {num}")

            if palpite == resultado:
                saldo += aposta
                print(f"Você ganhou! Novo saldo: R$ {saldo}")
            else:
                saldo -= aposta
                print(f"Você perdeu! Novo saldo: R$ {saldo}")

        # 2. CARA OU COROA
        if escolha == "2":
            palpite = input("Cara ou Coroa? (c/r): ").lower()
            if palpite not in ["c", "r"]:
                print("Escolha inválida!")
                continue

            resultado = random.choice(["c", "r"])

            print("Resultado:", "Cara" if resultado == "c" else "Coroa")

            if palpite == resultado:
                saldo += aposta
                print(f"Você ganhou! Novo saldo: R$ {saldo}")
            else:
                saldo -= aposta
                print(f"Você perdeu! Novo saldo: R$ {saldo}")

        # 3. NÚMERO DA SORTE
        if escolha == "3":
            try:
                numero = int(input("Escolha um número de 1 a 5: "))
            except:
                print("Número inválido!")
                continue

            if numero < 1 or numero > 5:
                print("Número fora do intervalo!")
                continue

            sorteado = random.randint(1, 5)
            print(f"Número sorteado: {sorteado}")

            if numero == sorteado:
                saldo += aposta * 4
                print(f"Acertou! Ganhou x4! Novo saldo: R$ {saldo}")
            else:
                saldo -= aposta
                print(f"Não foi dessa vez... Novo saldo: R$ {saldo}")
        print("\nRetornando ao menu...\n")

def encontrar_resposta(mensagem):
    mensagem = mensagem.lower()
    res = resolver_expressao(mensagem)
    if res:
        return res
    if "aposta" in mensagem or "jogo" in mensagem or "jogar" in mensagem:
        return jogo_aposta()
    for chave in regras:
        if chave in mensagem:
            if chave in ["hora", "horas", "que horas são"]:
                return pegar_hora()

            return random.choice(regras[chave])

    return None

def resolver_expressao(mensagem):
    padrao = r"[0-9\+\-\*/\(\)\.\^]+"
    possivel= re.findall(padrao,mensagem.replace(" ", ""))

    if not possivel:
        return None
    
    expressao = possivel[0]

    expressao = expressao.replace("^","**")

    try:
        resultado = eval(expressao)
        return f"O resultado de {expressao} é {resultado}"
    except:
        return None

def iniciar_chatbot():
    print("Olá, sou o PPCHAT. Posso fazer piadas, executar jogos, resolver expressões matemáticas, ser um ouvinte virtual e informar a hora atual. Como posso ajudar você hoje?\n")

    while True:
        mensagem = input("Você: ")

        if mensagem.lower() == "sair":
            print("Chatbot: Até logo!")
            break

        resposta = encontrar_resposta(mensagem)

        if resposta:
            print("Chatbot:", resposta)
        else:
            print("Chatbot: Desculpe, não entendi.")

iniciar_chatbot()
