# convert Turing Machine into a clockwise turing machine
# python TM2CTM.py ..\TMDefs\Regel110.tm
import re
import sys

class UniqueSymbolList:
    def __init__(self):
        self.symbols = []

    def add_symbol(self, symbol):
        if symbol not in self.symbols:
            self.symbols.append(symbol)

    def get_symbols(self):
        return self.symbols
    def get_len(self):
        return len(self.symbols)
        
def read_text_file(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
            return content
    except FileNotFoundError:
        print(f"Die Datei {file_path} wurde nicht gefunden.")
    except Exception as e:
        print(f"Ein Fehler ist aufgetreten: {e}")

def parse_input(input_string):
    # Define regex patterns for each part of the input
    initial_state_pattern = re.compile(r'\([a-zA-Z0-9]+,[a-zA-Z0-9]+\)')
    tape_pattern = re.compile(r'\([a-zA-Z0-9]+\)')
    transitions_pattern = re.compile(r'\([a-zA-Z0-9]+,[a-zA-Z0-9]+,[a-zA-Z0-9]+,[a-zA-Z0-9]+,[R|L]\)')

    # Find all matches
    initial_state = initial_state_pattern.findall(input_string)
    tape = tape_pattern.findall(input_string)
    transitions = transitions_pattern.findall(input_string)

    # Parse the initial state
    initial_state = initial_state[0].strip('()').split(',')
    initial_state = (int(initial_state[0]), int(initial_state[1]))

    # Parse the tape
    tape = tape[0].strip('()')

    # Parse the transitions
    transitions = [tuple(transition.strip('()').split(',')) for transition in transitions]

    return initial_state, tape, transitions
    
def convert(tape, transitions):
    symbolList = UniqueSymbolList()
    stateList = UniqueSymbolList()

    for transition in transitions:
        if 5 == len(transition):
          stateList.add_symbol(transition[0])
          symbolList.add_symbol(transition[1])
          stateList.add_symbol(transition[2])
          symbolList.add_symbol(transition[3])
    
    print(stateList.get_symbols(), stateList.get_len())
    print(symbolList.get_symbols())
     
# main
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Bitte geben Sie den Dateipfad als Argument an.")
        sys.exit(1)

    file_path = sys.argv[1]
    input_string = read_text_file(file_path)

    if input_string is not None:
        initial_state, tape, transitions = parse_input(input_string)
        print("#clockwise turing machine converterd from:",file_path)
        print(initial_state)
        print(tape)
        convert(tape,transitions)       

        

