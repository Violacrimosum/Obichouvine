package s6.prog6.obichouvine.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Move;
import s6.prog6.obichouvine.models.Pawn;
import s6.prog6.obichouvine.models.Block.BlockState;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.IA;
import s6.prog6.obichouvine.models.ia.MiniMax;

public class GameController {
	private Board board;

	private Vector2 cursorPos;
	private int button;

	private Block selectedPawn;

	private PawnType turn;
	private float turnNum;
	
	private boolean isIATurn = false;
	private Player p1, p2;
	enum Keys {
		CLICK
	}

	static Map<Keys, Boolean> keys = new HashMap<GameController.Keys, Boolean>();
	static {
		keys.put(Keys.CLICK, false);
	};

	public GameController(Board board, PawnType turn, Player p1, Player p2){
		this.board = board;
		this.turn = turn;
		this.p1 = p1;
		this.p2 = p2;
		System.out.println(p1.getTeam());
		System.out.println(p2.getTeam());
		System.out.println(turn);
		this.isIATurn = this.nextTurnIa();
		this.turnNum = (float) 1;

	}

	public Move update(float delta) {
		return processInput();
	}

	public void clickReleased(Vector2 cursorPos, int button) {
		this.button = button;
		this.cursorPos = cursorPos;
		this.refactorCursorPos();
		keys.get(keys.put(Keys.CLICK, true));
		System.out.println("RELEASED : "+cursorPos);

	}

	public void clickPressed(Vector2 cursorPos, int button) {
		this.button = button;
		this.cursorPos = cursorPos;
		this.refactorCursorPos();
		keys.get(keys.put(Keys.CLICK, false));
	}

	private Move processInput() {
		// TODO Auto-generated method stub
		Move res = null;
		if(keys.get(Keys.CLICK)){
			if(this.selectedPawn==null){
				System.out.println("Test");
				if(this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() != PawnType.VIDE &&
						this.board.board[(int)cursorPos.x][(int)cursorPos.y].getPawn().getType() == turn){
					System.out.println("Selected ["+cursorPos.x+","+cursorPos.y+"]");
					this.selectedPawn = this.board.board[(int)cursorPos.x][(int)cursorPos.y];
					this.board.highlightMoves((int)cursorPos.x, (int)cursorPos.y, true);
				}
			}
			else{
				int xStart = (int) ((this.selectedPawn.getPosition().x- board.offsetX)/Block.SIZE);
				int yStart = (int) ((this.selectedPawn.getPosition().y- board.offsetY)/Block.SIZE);
				if((xStart == (int)cursorPos.x) && (yStart == (int)cursorPos.y));
				else if(board.deplacement(new Move(xStart,
						yStart,
						(int)cursorPos.x, 
						(int)cursorPos.y)) == 3 ){
					System.out.println("Ca marche !!");
				}
				else{
					this.selectedPawn = null;
					res = new Move(xStart,
							yStart,
							(int)cursorPos.x, 
							(int)cursorPos.y, this.turn, (int)this.turnNum);
					this.board.highlightMoves((int)cursorPos.x, (int)cursorPos.y, false);
					this.switchTurn();
					if(this.nextTurnIa())
						this.isIATurn = true;
					this.turnNum += 0.5;
				}

			}
			keys.get(keys.put(Keys.CLICK, false));
			
		}
		if(this.isIATurn){
			while(this.nextTurnIa()){
				if(turn.equals(p1.getTeam()) && p1 instanceof IA){
					MiniMax ia = (MiniMax)p1;
					ia.jouer(board);
					this.turnNum += 0.5;
				}
				if(turn.equals(p2.getTeam()) && p2 instanceof IA){
					MiniMax ia = (MiniMax)p2;
					ia.jouer(board);
					this.turnNum += 0.5;
				}
			}
		}
		return res;
	}


	private boolean nextTurnIa() {
		// TODO Auto-generated method stub
		if(this.turn==PawnType.MOSCOVITE)
			return p1 instanceof IA;
		else if (this.turn==PawnType.SUEDOIS)
			return p2 instanceof IA;
		return false;
	}

	private void switchTurn() {
		// TODO Auto-generated method stub
		turn = (turn==PawnType.MOSCOVITE)? PawnType.SUEDOIS : PawnType.MOSCOVITE;
	}

	private void refactorCursorPos(){
		if(cursorPos.x < board.offsetX)
			cursorPos.x = 0;
		else if(cursorPos.x > board.offsetX + (board.xBoard)*Block.SIZE)
			cursorPos.x = board.xBoard-1;//(board.offsetX + (board.xBoard - 1)*Block.SIZE);
		else
			cursorPos.x = (int)((cursorPos.x - board.offsetX)/Block.SIZE);

		if(cursorPos.y < board.offsetY)
			cursorPos.y = board.yBoard-1;//board.offsetY;
		else if(cursorPos.y > board.offsetY + (board.yBoard)*Block.SIZE)
			cursorPos.y = 0;//board.offsetY + (board.yBoard)*Block.SIZE;
		else
			cursorPos.y = (int)(-((cursorPos.y - board.offsetY)/Block.SIZE)+(board.yBoard));

	}	
}
