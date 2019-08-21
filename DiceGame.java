package dicegame;

import java.util.Random;
import java.util.Scanner;

import dicegame.DiceGame.Dice;

public class DiceGame {

	public static class Dice {
		private Random rand;
		private int val;
		
		public Dice() {
			this.val = 0;
			this.rand = new Random();
		}
		
		public int getVal() {
			return val;
		}

		private void setVal(int val) {
			this.val = val;
		}

		public int Roll() {
			int r =  1 + this.rand.nextInt(6);
			this.setVal(r);
			return r;
		}
	}
	
	private Dice[] die;
	private int score;
	private static final int DICEAMOUNT = 5;
	private static final int TURNAMOUNT = 3;
	private static final int THROWAMOUNT = 3;
	
	public DiceGame() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Indicate number of players:(cannot be 0) ");
		String play = sc.nextLine();
		int player = Integer.parseInt(play);
		int turnplay = player * TURNAMOUNT;
		int[] finalscore = new int[player];
		this.die = new Dice[DICEAMOUNT];
		for(int i = 0; i < DICEAMOUNT; i++) {
			this.die[i] = new Dice();
		}
		int tiradas, turnos = 0;
		while(turnos < turnplay) {
			tiradas = 0; score = 0;
			while(tiradas < THROWAMOUNT) {
				if(tiradas >= 1) {
					System.out.println("Enter amount of dice to be rerolled: ");
					String s = sc.nextLine();
					int throwAgain = Integer.parseInt(s);
					while(throwAgain > 0) {
						System.out.println("Dice number to be rerolled: (0-4)");
						String so = sc.nextLine();
						int i = Integer.parseInt(so);
						die[i].Roll();
						throwAgain--;
					}
					showValue(this.die);
					tiradas++;
					if(tiradas == THROWAMOUNT) {
						score = Scoreset(this.die, score, tiradas);
					}
				}
				else {
					System.out.println("First Throw: ");
					for(int i = 0; i < this.die.length; i++) {
						this.die[i].setVal(this.die[i].Roll());
					}
					showValue(this.die);
					tiradas++;
					System.out.println("Do you want to roll again? \n Please write if Yes/No");
					String str = sc.nextLine();
					if(throwAgain(str)) {
						continue;
					}
					else {
						score = Scoreset(this.die, score, tiradas);
						tiradas = TURNAMOUNT;
					}
				}
			}
			System.out.println("Your score is : " + score);
			finalscore[turnos % player] += score;
			turnos++;
		}
		System.out.println("Your final scores are : ");
		showFinalScore(finalscore);
		sc.close();
	}

	private void showFinalScore(int[] finalcore) {
		for(int i = 0; i < finalcore.length; i++) {
			System.out.println(finalcore[i]);
		}
	}

	private boolean throwAgain(String s) {
		return s.equals("Yes");
	}

	public int Scoreset(Dice[] dies, int score, int tiros) {
		int ein = 0, zwei = 0, drei = 0, vier = 0, funfz = 0, sechs = 0;
		for(int i = 0; i < dies.length; i++) {
			if(dies[i].getVal() == 1) ein++;
			if(dies[i].getVal() == 2) zwei++;
			if(dies[i].getVal() == 3) drei++;
			if(dies[i].getVal() == 4) vier++;
			if(dies[i].getVal() == 5) funfz++;
			if(dies[i].getVal() == 6) sechs++;
		}
		if(ein == 5 || zwei == 5 || drei == 5 || vier == 5 || funfz == 5 || sechs == 6) {//Yahtzee
			if(tiros == 1) {
				score += 65;
			}
			else {
				score =+ 60;
			}
		}
		else if(ein == 4 || zwei == 4 || drei == 4 || vier == 4 || funfz == 4 || sechs == 4) {//Poker
			if(tiros == 1) {
				score += 55;
			}
			else {
				score += 50;
			}
		}
		else if((ein == 1 && zwei == 1 && drei == 1 && vier == 1 && funfz == 1) ||
				(zwei ==1 && drei == 1 && vier == 1 && funfz == 1 && sechs == 1)) {//Escalera
			if(tiros == 1) {
				score += 45;
			}
			else {
				score += 40;
			}
		}
		else {//Chance
			for(int i = 0; i < dies.length; i++) {
				score += dies[i].getVal();
			}
		}
		return score;
	}
	
	public void showValue(Dice[] dies) {
		for(int i = 0; i < dies.length; i++) {
			System.out.println(dies[i].getVal());
		}
	}
	
	public static void main(String[] args) {
		DiceGame d = new DiceGame();
	}
}
