# Pt3. xifratge AES i per blocs

Codigo implementado:
## Scanner añadido para pedir el texto a cifrar
```
Scanner scanner = new Scanner(System.in)) {
            System.out.println("Quin text vols xifrar?");
            textPla = scanner.nextLine();
            System.out.println("Text pla: " + textPla);
        }
```

## Aplicar padding para que para que el texto sea multiple de BLOCK_SIZE
```
private static byte[] aplicarPadding(byte[] input) {
        int faltant = input.length % BLOCK_SIZE;
        int paddedlenght = BLOCK_SIZE - faltant;
        byte[] paddedInput = new byte[input.length + paddedlenght];
        
        System.arraycopy(input, 0, paddedInput, 0, input.length);

        byte paddingvalue = (byte) paddedlenght;

        for (int i = input.length; i <paddedInput.length; i++){
            paddedInput[i] = paddingvalue;
        }
        
        return paddedInput;
    }
```
### Calula cuant padding cal per arribar a ser multiple de BLOC_SIZE
```
int faltant = input.length % BLOCK_SIZE;
```


### Recorreix el nou Array i afegeix el padding faltant, aquest sent igual al numero de padding faltant
``` 
for (int i = input.length; i <paddedInput.length; i++){
    paddedInput[i] = paddingvalue;
}
```


## Xifratge final per XOR

```
private static byte[] xifrarBloc(byte[] bloc, byte[] clau) {
        byte[] blocXifrat = new byte[bloc.length]; 
        for (int i = 0; i < bloc.length;i++){
            blocXifrat[i] = (byte) (bloc[i] ^ clau[i]);
        }
        return blocXifrat;
    }
```