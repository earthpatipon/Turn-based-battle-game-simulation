import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Arena {

	public enum Row {
		Front, Back
	}; // enum for specifying the front or back row

	public enum Team {
		A, B
	}; // enum for specifying team A or B

	private Player[][] teamA = null; // two dimensional array representing the players of Team A
	private Player[][] teamB = null; // two dimensional array representing the players of Team B

	public static final int MAXROUNDS = 100; // Max number of turn
	public static final int MAXEACHTYPE = 3; // Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");

	private int numRounds = 0; // keep track of the number of rounds so far

	// my variables
	private int numRowPlayers;

	// private int turns=0;
	/**
	 * Constructor.
	 * 
	 * @param _numRows       is the number of row in each team.
	 * @param _numRowPlayers is the number of player in each row.
	 */
	public Arena(int _numRowPlayers) {
		// INSERT YOUR CODE HERE
		this.numRowPlayers = _numRowPlayers;
		this.teamA = new Player[2][numRowPlayers];
		this.teamB = new Player[2][numRowPlayers];

		//// Keep this block of code. You need it for initialize the log file.
		//// (You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////

	}

	/**
	 * Returns true if "player" is a member of "team", false otherwise. Assumption:
	 * team can be either Team.A or Team.B
	 * 
	 * @param playerp
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team) {
		// INSERT YOUR CODE HERE
		if (player.getTeam() == team)
			return true;
		else
			return false;
	}

	/**
	 * This methods receives a player configuration (i.e., team, type, row, and
	 * position), creates a new player instance, and places him at the specified
	 * position.
	 * 
	 * @param team     is either Team.A or Team.B
	 * @param pType    is one of the Player.Type {Healer, Tank, Samurai, BlackMage,
	 *                 Phoenix}
	 * @param row      either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position
	 *                 starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position) {
		// INSERT YOUR CODE HERE
		int x, y;
		y = position - 1;

		if (team == Arena.Team.A) {
			if (row == Arena.Row.Front) {
				x = 0;
				this.teamA[x][y] = new Player(pType);
				teamA[x][y].setTeam(team);
				teamA[x][y].setPosition(row, position);
			} else {
				x = 1;
				this.teamA[x][y] = new Player(pType);
				teamA[x][y].setTeam(team);
				teamA[x][y].setPosition(row, position);
			}
		}

		else { // team B
			if (row == Arena.Row.Front) {
				x = 0;
				this.teamB[x][y] = new Player(pType);
				teamB[x][y].setTeam(team);
				teamB[x][y].setPosition(row, position);
			} else {
				x = 1;
				this.teamB[x][y] = new Player(pType);
				teamB[x][y].setTeam(team);
				teamB[x][y].setPosition(row, position);
			}
		}
	}

	/**
	 * Validate the players in both Team A and B. Returns true if all of the
	 * following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly
	 * numRow*numRowPlayers players. 2. There can be at most MAXEACHTYPE players of
	 * each type in each team. For example, if MAXEACHTYPE = 3 then each team can
	 * have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * 
	 * @return
	 */
	public boolean validatePlayers() {
		// INSERT YOUR CODE HERE
		int healera = 0, tanka = 0, samuraia = 0, blackmagea = 0, phoenixa = 0;
		int healerb = 0, tankb = 0, samuraib = 0, blackmageb = 0, phoenixb = 0;
		// check no.1
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < this.numRowPlayers; j++) {

				if (this.teamA[i][j] == null || this.teamB[i][j] == null)
					return false;

				else {
					switch (this.teamA[i][j].getType()) {
						case Healer:
							healera++;
							break;
						case Tank:
							tanka++;
							break;
						case Samurai:
							samuraia++;
							break;
						case BlackMage:
							blackmagea++;
							break;
						case Phoenix:
							phoenixa++;
							break;
					}
					switch (this.teamB[i][j].getType()) {
						case Healer:
							healerb++;
							break;
						case Tank:
							tankb++;
							break;
						case Samurai:
							samuraib++;
							break;
						case BlackMage:
							blackmageb++;
							break;
						case Phoenix:
							phoenixb++;
							break;
					}
					// check no.2
					if (healera > MAXEACHTYPE || tanka > MAXEACHTYPE || samuraia > MAXEACHTYPE
							|| blackmagea > MAXEACHTYPE || phoenixa > MAXEACHTYPE)
						return false;
					else if (healerb > MAXEACHTYPE || tankb > MAXEACHTYPE || samuraib > MAXEACHTYPE
							|| blackmageb > MAXEACHTYPE || phoenixb > MAXEACHTYPE)
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * 
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team) {
		// INSERT YOUR CODE HERE
		double SumHP = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < team[0].length; j++) {
				SumHP += team[i][j].getCurrentHP();
			}
		}
		return SumHP;
	}

	/**
	 * Return the team (either teamA or teamB) whose number of alive players is
	 * higher than the other.
	 * 
	 * If the two teams have an equal number of alive players, then the team whose
	 * sum of HP of all the players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * 
	 * @return
	 */
	public Player[][] getWinningTeam() {

		// INSERT YOUR CODE HERE
		int countA = 0;
		int countB = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < this.numRowPlayers; j++) {
				if (this.teamA[i][j].isAlive())
					countA++;
				if (this.teamB[i][j].isAlive())
					countB++;
			}
		}
		// search winner
		if (countA > countB) {
			System.out.println("@@@" + " " + "Team A won.");
			return this.teamA;
		} else if (countA < countB) {
			System.out.println("@@@" + " " + "Team B won.");
			return this.teamB;
		} else {
			if (getSumHP(teamA) > getSumHP(teamB)) {
				System.out.println("@@@" + " " + "Team A won.");
				return this.teamA;
			} else if (getSumHP(teamA) < getSumHP(teamB)) {
				System.out.println("@@@" + " " + "Team B won.");
				return this.teamB;
			} else {
				System.out.println("@@@" + " " + "Team A won.");
				return this.teamA;
			}

		}
	}

	/**
	 * This method simulates the battle between teamA and teamB. The method should
	 * have a loop that signifies a round of the battle. In each round, each player
	 * in teamA invokes the method takeAction(). The players' turns are ordered by
	 * its position in the team. Once all the players in teamA have invoked
	 * takeAction(), not it is teamB's turn to do the same.
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated. 2. The number of rounds
	 * exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by
	 * getWinningTeam().
	 */
	public void startBattle() {

		// INSERT YOUR CODE
		boolean end = false;
		while (true) {

			if (this.numRounds == MAXROUNDS || getSumHP(teamA) == 0 || getSumHP(teamB) == 0) {
				break;
			}
			this.numRounds++;
			System.out.println("@" + " " + "Round" + " " + numRounds);
			// team A
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < this.numRowPlayers; j++) {
					if (teamA[i][j].isAlive()) {
						teamA[i][j].takeAction(this);
						// turns += teamA[i][j].inTurn;
						if (getSumHP(teamB) == 0) {
							end = true;
							break;
						}
						/*
						 * else if(turns == 10){ this.setOffCursed(teamA); turns = 0; }
						 */
					}
				}
				if (end)
					break;
			}
			this.setOffCursed(teamA);

			// team B
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < this.numRowPlayers; j++) {
					if (teamB[i][j].isAlive()) {
						teamB[i][j].takeAction(this);
						// turns += teamB[i][j].inTurn;
						if (getSumHP(teamA) == 0) {
							end = true;
							break;
						}
						/*
						 * else if(turns == 10){ this.setOffCursed(teamA); turns = 0; }
						 */
					}
				}
				if (end)
					break;
			}
			this.setOffCursed(teamB);
			this.logAfterEachRound();
			Arena.displayArea(this, true);
		}
		this.getWinningTeam();
	}

	/**
	 * This method displays the current area state, and is already implemented for
	 * you. In startBattle(), you should call this method once before the battle
	 * starts, and after each round ends.
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose) {
		if (arena == null)
			return;

		StringBuilder str = new StringBuilder();
		if (verbose) {
			str.append(String.format("%38s   %35s", "Team A", "") + "\t\t" + String.format("%-33s%-35s", "", "Team B")
					+ "\n");
			str.append(String.format("%38s", "BACK ROW") + String.format("%38s", "FRONT ROW") + "  |  "
					+ String.format("%-38s", "FRONT ROW") + "\t" + String.format("%-38s", "BACK ROW") + "\n");
			for (int i = 0; i < arena.teamA[0].length; i++) {
				str.append(String.format("%38s", arena.teamA[1][i]) + String.format("%38s", arena.teamA[0][i]) + "  |  "
						+ String.format("%-38s", arena.teamB[0][i]) + String.format("%-38s", arena.teamB[1][i]) + "\n");
			}
		}

		str.append("@ Total HP of Team A = " + getSumHP(arena.teamA) + ". @ Total HP of Team B = "
				+ getSumHP(arena.teamB) + "\n\n");
		System.out.print(str.toString());

	}

	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP
	 * of teamB) into the log file. You are not to modify this method, however, this
	 * method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the
	 * output look something like:
	 * 
	 * 1 47415.0 49923.0 2 44977.0 46990.0 3 42092.0 43525.0 4 44408.0 43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round
	 * numbers, sum of HP of teamA, and sum of HP of teamB respectively.
	 */
	private void logAfterEachRound() {
		try {
			Files.write(logFile,
					Arrays.asList(new String[] { numRounds + "\t" + getSumHP(teamA) + "\t" + getSumHP(teamB) }),
					StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// *********************************************************************************************************
	// MY METHOD
	public static Player findLowestPercentTarget(Player[][] target) {

		double lowestTarget = 99999;
		int x = -1, y = -1;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < target[0].length; j++) {
				if (target[i][j].isAlive()) {
					double lowTarget = (target[i][j].getCurrentHP() / target[i][j].getMaxHP()) * 100;
					if (lowTarget < lowestTarget) {
						lowestTarget = lowTarget;
						x = i;
						y = j;
					}
				}
			}
		}
		return target[x][y];
	}

	public static Player findLowestTarget(Player[][] target) {

		double totalHP = 0;

		for (int j = 0; j < target[0].length; j++) {
			totalHP = totalHP + target[0][j].getCurrentHP();
		}

		if (totalHP != 0) { // FRONT ROW
			double lowestTarget = 99999;
			int y = -1;
			for (int j = 0; j < target[0].length; j++) {
				if (target[0][j].isAlive()) {
					double lowTarget = target[0][j].getCurrentHP();

					if (lowTarget < lowestTarget) {
						lowestTarget = lowTarget;
						y = j;
					}
				}
			}
			if (lowestTarget == 99999)
				return null;
			else
				return target[0][y];
		}

		else { // BACK ROW
			double lowestTarget = 99999;
			int y = -1;
			for (int j = 0; j < target[1].length; j++) {
				if (target[1][j].isAlive()) {
					double lowTarget = target[1][j].getCurrentHP();

					if (lowTarget < lowestTarget) {
						lowestTarget = lowTarget;
						y = j;
					}
				}
			}
			if (lowestTarget == 99999)
				return null;
			else
				return target[1][y];
		}
	}

	public static Player findDeadTarget(Player[][] target) {

		int x = -1, y = -1;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < target[0].length; j++) {
				if (!target[i][j].isAlive()) {
					x = i;
					y = j;
					return target[x][y];
				}
			}
		}
		return null;
	}

	public Player[][] getTeamA() {
		return teamA;
	}

	public Player[][] getTeamB() {
		return teamB;
	}

	public static Player atkTaunt(Player[][] target) {

		int x = -1, y = -1;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < target[0].length; j++) {
				if (target[i][j].isAlive() && target[i][j].isTaunting()) {
					x = i;
					y = j;
					return target[x][y];
				}
			}
		}
		return null;
	}

	public boolean findTaunt(Player[][] target) {

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < target[0].length; j++) {
				if (target[i][j].isAlive() && target[i][j].isTaunting()) {
					return true;
				}
			}
		}
		return false;
	}

	public void setOffCursed(Player[][] target) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < target[0].length; j++) {
				if (target[i][j].isAlive() && target[i][j].isCursed()) {
					target[i][j].cursed = false;
				}
			}
		}
	}
	// *********************************************************************************************************

}
