
public class Player {

	public enum PlayerType {
		Healer, Tank, Samurai, BlackMage, Phoenix
	};

	private PlayerType type; // Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage,
								// or Phoenix
	private double maxHP; // Max HP of this player
	private double currentHP; // Current HP of this player
	private double atk; // Attack power of this player

	// my variables
	private int specialTurns;
	public boolean cursed; // being cursed
	private boolean taunting;
	private Arena.Team team;
	public int turn;
	public boolean alive = true;
	private boolean checkTaunt;
	private Arena.Row row;
	private int position;

	/**
	 * Constructor of class Player, which initializes this player's type, maxHP,
	 * atk, numSpecialTurns, as specified in the given table. It also reset the
	 * internal turn count of this player.
	 * 
	 * @param _type
	 */
	public Player(PlayerType _type) {
		// INSERT YOUR CODE HERE
		this.type = _type;

		switch (this.type) {

			case Healer:
				this.maxHP = 4790;
				this.currentHP = 4790;
				this.atk = 238;
				this.specialTurns = 4;
				this.alive = true;
				this.cursed = false;
				this.taunting = false;
				break;
			case Tank:
				this.maxHP = 6240;
				this.currentHP = 6240;
				this.atk = 315;
				this.specialTurns = 4;
				this.alive = true;
				this.cursed = false;
				this.taunting = false;
				break;
			case Samurai:
				this.maxHP = 4905;
				this.currentHP = 4905;
				this.atk = 368;
				this.specialTurns = 3;
				this.alive = true;
				this.cursed = false;
				this.taunting = false;
				break;
			case BlackMage:
				this.maxHP = 4175;
				this.currentHP = 4175;
				this.atk = 339;
				this.specialTurns = 4;
				this.alive = true;
				this.cursed = false;
				this.taunting = false;
				break;
			case Phoenix:
				this.maxHP = 5175;
				this.currentHP = 5175;
				this.atk = 209;
				this.specialTurns = 8;
				this.alive = true;
				this.cursed = false;
				this.taunting = false;
				break;
		}
	}

	/**
	 * Returns the current HP of this player
	 * 
	 * @return
	 */
	public double getCurrentHP() {
		// INSERT YOUR CODE HERE
		return this.currentHP;
	}

	/**
	 * Returns type of this player
	 * 
	 * @return
	 */
	public Player.PlayerType getType() {
		// INSERT YOUR CODE HERE
		return this.type;
	}

	/**
	 * Returns max HP of this player.
	 * 
	 * @return
	 */
	public double getMaxHP() {
		// INSERT YOUR CODE HERE
		return this.maxHP;
	}

	/**
	 * Returns whether this player is being cursed.
	 * 
	 * @return
	 */
	public boolean isCursed() {
		// INSERT YOUR CODE HERE
		if (this.cursed)
			return true;
		else
			return false;
	}

	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * 
	 * @return
	 */
	public boolean isAlive() {
		// INSERT YOUR CODE HERE
		if (this.alive) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns whether this player is taunting the other team.
	 * 
	 * @return
	 */
	public boolean isTaunting() {
		// INSERT YOUR CODE HERE
		if (this.taunting == true)
			return true;
		else
			return false;
	}

	public void attack(Player target) {
		// INSERT YOUR CODE HERE
		target.currentHP = target.currentHP - this.atk;
		if (target.currentHP <= 0) {
			target.currentHP = 0;
			target.alive = false;
			target.taunting = false;
			target.cursed = false;
		}

		// PRINT RESULT
		System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type + "}"
				+ " Attacks " + target.team + "[" + target.row + "]" + "[" + target.position + "]" + " {" + target.type
				+ "}");

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MY METHOD
	/**
	 * Special Ability of Healer
	 * 
	 * @return
	 */
	public void heal(Player target) {

		if (target.isCursed()) {
			// null
		} else {
			target.currentHP = target.maxHP * 0.3 + target.currentHP;
			if (target.currentHP > target.maxHP)
				target.currentHP = target.maxHP;
			System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type
					+ "}" + " Heal " + target.team + "[" + target.row + "]" + "[" + target.position + "]" + " {"
					+ target.type + "}");
		}
	}

	/**
	 * Special Ability of Tank
	 * 
	 * @return
	 */
	public void taunt() {

		this.taunting = true;
		System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type + "}"
				+ " is Taunting");
	}

	/**
	 * Special Ability of Samurai
	 * 
	 * @return
	 */
	public void doubleSlash(Player target) {

		double atktemp = this.atk * 2;
		target.currentHP -= atktemp;
		if (target.currentHP <= 0) { // to make it dead completely by turning his or her status off
			target.currentHP = 0;
			target.alive = false;
			target.cursed = false;
			target.taunting = false;
		}
		System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type + "}"
				+ " Double-Slashes " + target.team + "[" + target.row + "]" + "[" + target.position + "]" + " {"
				+ target.type + "}");
	}

	/**
	 * Special Ability of DarkMage
	 * 
	 * @return
	 */
	public void curse(Player target) {

		target.cursed = true;
		System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type + "}"
				+ " Curses " + target.team + "[" + target.row + "]" + "[" + target.position + "]" + " {" + target.type
				+ "}");
	}

	/**
	 * Special Ability of Phoenix
	 * 
	 * @return
	 */
	public void revive(Player target) {

		target.alive = true;
		target.turn = 0;
		target.cursed = false;
		target.taunting = false;
		target.currentHP = target.maxHP * 0.3;
		System.out.println("# " + this.team + "[" + this.row + "]" + "[" + this.position + "]" + " {" + this.type + "}"
				+ " Revives " + target.team + "[" + target.row + "]" + "[" + target.position + "]" + " {" + target.type
				+ "}");
	}

	public Arena.Team getTeam() {
		// to return whether what is his or her team
		return this.team;
	}

	public void setTeam(Arena.Team team) {
		// use this method in add player
		// to keep value teamA or teamB in each player
		this.team = team;
	}

	public void setPosition(Arena.Row row, int position) {
		// use this method in add player
		// to keep value row and position
		// to being easy for printing result in screen_output
		this.row = row;
		this.position = position;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam) {
		// INSERT YOUR CODE HERE
		// see algorithm of checkTaunt, atkTaunt, findLowestTarget, findDeadTarget
		// and findLowestPercentTarget in Arena
		switch (this.getType()) {
			case Healer:
				this.heal(Arena.findLowestPercentTarget(myTeam));
				break;
			case Tank:
				this.taunt();
				break;
			case Samurai:
				if (checkTaunt)
					this.doubleSlash(Arena.atkTaunt(theirTeam));
				else
					this.doubleSlash(Arena.findLowestTarget(theirTeam));
				break;
			case BlackMage:
				if (checkTaunt) {
					this.curse(Arena.atkTaunt(theirTeam));
				} else {
					this.curse(Arena.findLowestTarget(theirTeam));
				}
				break;
			case Phoenix:
				this.revive(Arena.findDeadTarget(myTeam));
				break;
		}
	}

	/**
	 * This method is called by Arena when it is this player's turn to take an
	 * action. By default, the player simply just "attack(target)". However, once
	 * this player has fought for "numSpecialTurns" rounds, this player must perform
	 * "useSpecialAbility(myTeam, theirTeam)" where each player type performs his
	 * own special move.
	 * 
	 * @param arena
	 */
	public void takeAction(Arena arena) {
		// INSERT YOUR CODE HERE
		this.turn++;
		if (this.taunting)
			this.taunting = false; // turn off taunt status on tank

		if (this.getTeam() == Arena.Team.A) {
			// pointer to teamB
			Player[][] tempB = arena.getTeamB();

			this.checkTaunt = arena.findTaunt(tempB);
			switch (this.getType()) {
				case Healer:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(arena.getTeamA(), null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempB));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamB()));
					break;
				case Tank:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempB));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamB()));
					break;
				case Samurai:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, arena.getTeamB());
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempB));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamB()));
					break;
				case BlackMage:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, arena.getTeamB());
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempB));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamB()));
					break;
				case Phoenix:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(arena.getTeamA(), null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempB));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamB()));
					break;
			}
		}

		else { // Arena.Team.B
				// ponters to teamA
			Player[][] tempA = arena.getTeamA();
			this.checkTaunt = arena.findTaunt(tempA);

			switch (this.getType()) {
				case Healer:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(arena.getTeamB(), null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempA));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamA()));
					break;
				case Tank:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempA));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamA()));
					break;
				case Samurai:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, arena.getTeamA());
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempA));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamA()));
					break;
				case BlackMage:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(null, arena.getTeamA());
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempA));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamA()));
					break;
				case Phoenix:
					if (turn % this.specialTurns == 0)
						this.useSpecialAbility(arena.getTeamB(), null);
					else if (checkTaunt)
						this.attack(Arena.atkTaunt(tempA));
					else
						this.attack(Arena.findLowestTarget(arena.getTeamA()));
					break;
			}
		}
	}

	/**
	 * This method overrides the default Object's toString() and is already
	 * implemented for you.
	 */
	@Override
	public String toString() {
		return "[" + this.type.toString() + " HP:" + this.currentHP + "/" + this.maxHP + " ATK:" + this.atk + "]";
	}

}
