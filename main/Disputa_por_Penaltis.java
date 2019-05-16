/*
* Author: Wadson Pontes
* Date: 12/05/2019
* Version: 3.0
*/

// Segundo o G1 (http://g1.globo.com/fantastico/noticia/2014/05/estatisticas-mostram-que-em-disputa-de-penaltis-vence-quem-bate-primeiro.html):
// 70% dos pênaltis resultam em gol.
// Segundo o DuMoney (https://www.dumoney.com.br/economia-comportamental/as-estatisticas-que-provam-que-uma-disputa-de-penaltis-nao-e-loteria/):
// 7% dos pênaltis são defendidos.

package main;

import java.util.ArrayList;
import java.util.Scanner;
import times.*;

public class Disputa_por_Penaltis {
    public static ArrayList<Time> clubes = new ArrayList<Time>();
    public static Time timePadrao;

    public static final int precisao = 80;
    public static final int potencia = 85;
    public static final int reflexo = 11;
    public static final int defesa = 15;

    public static final int precisaoNoMeio = 89;
    public static final int potenciaNoMeio = 70;
    public static final int reflexoNoMeio = 30;
    public static final int defesaNoMeio = 30;

    public static Scanner scan = new Scanner(System.in);
    public static Time youTime;
    public static Time comTime;
    public static int youGols = 0;
    public static int comGols = 0;
    public static int youChutes = 0;
    public static int comChutes = 0;
    public static int youEscolha;
    public static int comEscolha;

    public static void main(String[] args) {
        Time abc = new Abc();
        Time americarn = new AmericaRN();
        Time barcelona = new Barcelona();
        Time corinthians = new Corinthians();
        Time cruzeiro = new Cruzeiro();
        Time flamengo = new Flamengo();
        Time gremio = new Gremio();
        Time palmeiras = new Palmeiras();
        Time realmadrid = new RealMadrid();
        Time saopaulo = new SaoPaulo();

        clubes.add(abc);
        clubes.add(americarn);
        clubes.add(barcelona);
        clubes.add(corinthians);
        clubes.add(cruzeiro);
        clubes.add(flamengo);
        clubes.add(gremio);
        clubes.add(palmeiras);
        clubes.add(realmadrid);
        clubes.add(saopaulo);
        
        timePadrao = corinthians;

        escolhaTime();
        placar();

        int i = 0;
        while (i < 5 && youGols + 5 - youChutes >= comGols && comGols + 5 - comChutes >= youGols) {
            youChute();
            resultadoYouChute();
            placar();
            if (youGols + 5 - youChutes < comGols || comGols + 5 - comChutes < youGols) break;
            comChute();
            resultadoComChute();
            placar();
            i++;
        }
        scan.nextLine();
        if (youGols == comGols) {
            System.out.println("Teremos cobranças alternadas.");

            while (youGols == comGols) {
                youChute();
                resultadoYouChute();
                placar();
                comChute();
                resultadoComChute();
                placar();
            }
            scan.nextLine();
        }
        if (youGols > comGols) System.out.println("Você ganhou!!!");
        else System.out.println("Você perdeu!!!");
        scan.nextLine();
    }

    public static void escolhaTime() {
        System.out.println("Escolha seu time: ");

        for (int j = 0; j < clubes.size(); j++) {
            System.out.println("( " + (j + 1) + " ) " + clubes.get(j).nome);
        }
        youEscolha = scan.nextInt();
        if ((youEscolha > 0 && youEscolha <= clubes.size()) == false) {
            youTime = timePadrao;
            clubes.remove(timePadrao);
        }
        else {
            youTime = clubes.get(youEscolha - 1);
            clubes.remove(youEscolha - 1);
        }
        comEscolha = (int) (Math.random() * clubes.size());
        comTime = clubes.get(comEscolha);
    }

    public static void youChute() {
        youChutes++;
        System.out.println("Escolha o lado que vai chutar: ");
        System.out.println("( 1 ) DIREITA");
        System.out.println("( 2 ) CENTRO");
        System.out.println("( 3 ) ESQUERDA");
        youEscolha = scan.nextInt();
        comEscolha = (int) (Math.random() * 3 + 1);
    }

    public static void resultadoYouChute() {
        int chance, dado, valor;
        if (youEscolha < 1 || youEscolha > 3) { // Chutou longe do gol.
            System.out.println("O " + youTime.nome + " chutou longe do gol.");
        }
        else {
            valor = precisao;
            if (youEscolha == 2) valor = precisaoNoMeio;
            chance = (int) (valor - 10 + (youTime.precisao / 5));
            dado = (int) (Math.random() * 100 + 1);

            if (dado > chance) { // Chutou para fora.
                System.out.println("O " + youTime.nome + " chutou para fora.");
            }
            else {
                if (youEscolha != comEscolha) { // Lados diferentes.
                    valor = reflexo;
                    if (youEscolha == 2) valor = reflexoNoMeio;
                    chance = (int) (valor - 10 + (comTime.reflexo / 5));
                    dado = (int) (Math.random() * 100 + 1);

                    if (dado > chance) { // Goleiro não conseguiu tirar com o pé.
                        youGols++;
                        if (comEscolha == 2) System.out.println("O goleiro nem pula e é gol do " + youTime.nome + ".");
                        else if (youEscolha == 2) System.out.println("O goleiro pula e a bola vai no meio, é gol do " + youTime.nome + ".");
                        else System.out.println("Bola para um lado e goleiro para o outro, é gol do " + youTime.nome + ".");
                    }
                    else { // Goleiro deixou o pés.
                        valor = potencia;
                        if (youEscolha == 2) valor = potenciaNoMeio;
                        chance = (int) (valor + (youTime.potencia - comTime.defesa) / 10);
                        dado = (int) (Math.random() * 100 + 1);

                        if (dado > chance) { // Goleiro conseguiu tirar com os pés.
                            System.out.println("O goleiro do " + comTime.nome + " tira a bola com a ponta dos pés.");
                        }
                        else { // Goleiro não consegue chegar na bola.
                            youGols++;
                            System.out.println("O goleiro não consegue chegar na bola e é gol do " + youTime.nome + ".");
                        }
                    }
                }
                else { // Goleiro foi no lado certo.
                    valor = potencia;
                    if (youEscolha == 2) valor = potenciaNoMeio;
                    chance = (int) (valor + (youTime.potencia - comTime.defesa) / 10);
                    dado = (int) (Math.random() * 100 + 1);

                    if (dado > chance) { // Goleiro defendeu.
                        System.out.println("Defesaça do goleiro do " + comTime.nome + ".");
                    }
                    else { // Goleiro desviou para dentro.
                        youGols++;
                        System.out.println("O goleiro espalma para dentro, é gol do " + youTime.nome + ".");
                    }
                }
            }
        }
        scan.nextLine();
        scan.nextLine();
    }

    public static void comChute() {
        comChutes++;
        comEscolha = (int) (Math.random() * 3 + 1);
        System.out.println("Escolha o lado que vai pular: ");
        System.out.println("( 1 ) DIREITA");
        System.out.println("( 2 ) CENTRO");
        System.out.println("( 3 ) ESQUERDA");
        youEscolha = scan.nextInt();
    }

    public static void resultadoComChute() {
        int chance, dado, valor;
        if (comEscolha < 1 || comEscolha > 3) { // Chutou longe do gol.
            System.out.println("O " + comTime.nome + " chutou longe do gol.");
        }
        else {
            valor = precisao;
            if (comEscolha == 2) valor = precisaoNoMeio;
            chance = (int) (valor - 10 + (comTime.precisao / 5));
            dado = (int) (Math.random() * 100 + 1);

            if (dado > chance) { // Chutou para fora.
                System.out.println("O " + comTime.nome + " chutou para fora.");
            }
            else {
                if (comEscolha != youEscolha) { // Lados diferentes.
                    valor = reflexo;
                    if (comEscolha == 2) valor = reflexoNoMeio;
                    chance = (int) (valor - 10 + (comTime.reflexo / 5));
                    dado = (int) (Math.random() * 100 + 1);

                    if (dado > chance) { // Goleiro não conseguiu tirar com o pé.
                        comGols++;
                        if (youEscolha == 2) System.out.println("O goleiro nem pula e é gol do " + comTime.nome + ".");
                        else if (comEscolha == 2) System.out.println("O goleiro pula e a bola vai no meio, é gol do " + comTime.nome + ".");
                        else System.out.println("Bola para um lado e goleiro para o outro, é gol do " + comTime.nome + ".");
                    }
                    else { // Goleiro deixou o pés.
                        valor = potencia;
                        if (comEscolha == 2) valor = potenciaNoMeio;
                        chance = (int) (valor + (comTime.potencia - youTime.defesa) / 10);
                        dado = (int) (Math.random() * 100 + 1);

                        if (dado > chance) { // Goleiro conseguiu tirar com os pés.
                            System.out.println("O goleiro do " + youTime.nome + " tira a bola com a ponta dos pés.");
                        }
                        else { // Goleiro não consegue chegar na bola.
                            comGols++;
                            System.out.println("O goleiro não consegue chegar na bola e é gol do " + comTime.nome + ".");
                        }
                    }
                }
                else { // Goleiro foi no lado certo.
                    valor = potencia;
                    if (comEscolha == 2) valor = potenciaNoMeio;
                    chance = (int) (valor + (comTime.potencia - youTime.defesa) / 10);
                    dado = (int) (Math.random() * 100 + 1);

                    if (dado > chance) { // Goleiro defendeu.
                        System.out.println("Defesaça do goleiro do " + youTime.nome + ".");
                    }
                    else { // Goleiro desviou para dentro.
                        comGols++;
                        System.out.println("O goleiro espalma para dentro, é gol do " + comTime.nome + ".");
                    }
                }
            }
        }
        scan.nextLine();
        scan.nextLine();
    }

    public static void placar() {
        System.out.println("---------------------------------------------");
        System.out.println("PLACAR: " + youTime.nome + " " + youGols + " X " + comGols + " " + comTime.nome);
        System.out.println("CHUTES: " + youTime.nome + " " + youChutes + " X " + comChutes + " " + comTime.nome);
        System.out.println("---------------------------------------------");
        System.out.println();
    }
}