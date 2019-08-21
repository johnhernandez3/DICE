package dicegame;

import dicegame.DiceGame.Dice;

public class DICEmain {

	public static void main(String[] args) {
		DiceGame d = new DiceGame();
		System.out.println(d.Scoreset(new Dice[5], 0, 0));
	}

}
