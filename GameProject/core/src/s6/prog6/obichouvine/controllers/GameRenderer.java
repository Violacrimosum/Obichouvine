package s6.prog6.obichouvine.controllers;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Pawn.TypeSuedois;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {

	private TextureRegion normalBlock;
	private TextureRegion escapeBlock;
	private TextureRegion escapeRussianBlock;
	private TextureRegion throneBlock;
	private TextureRegion moscovitBlock;
	
	private TextureRegion normalBlockHighlight;
	private TextureRegion escapeBlockHighlight;
	private TextureRegion escapeRussianBlockHighlight;
	private TextureRegion throneBlockHighlight;
	private TextureRegion moscovitBlockHighlight;

	private TextureAtlas atlas;
	
	private SpriteBatch spriteBatch;

	private BitmapFont font;
	
	private TextureRegion moscoPawn;
	private TextureRegion vikingSoldier;
	private TextureRegion viKing;

	private int ppuX, ppuY;

	private Board board;

	private char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M'};
	private char[] number = {'1','2','3','4','5','6','7','8','9','0','1','1'};
	
	public GameRenderer(Board b, SpriteBatch batch){
		this.board = b;
		this.spriteBatch = batch;
		
		font = new BitmapFont();
		loadTextures();
		ppuX = 1;
		ppuY = 1;
	}

	public void loadTextures(){
		atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
		normalBlock = atlas.findRegion("normalBlock");
		escapeBlock = atlas.findRegion("escapeBlock");
		throneBlock = atlas.findRegion("vikingBlock");
		moscovitBlock = atlas.findRegion("russianBlock");
		escapeRussianBlock = atlas.findRegion("escapeRussianBlock");
		
		normalBlockHighlight = atlas.findRegion("normalBlock");
		escapeBlockHighlight = atlas.findRegion("escapeBlock");
		throneBlockHighlight = atlas.findRegion("vikingBlock");
		moscovitBlockHighlight = atlas.findRegion("russianBlock");
		escapeRussianBlockHighlight = atlas.findRegion("escapeRussianBlock");
		
		moscoPawn = atlas.findRegion("Moscovit");
		vikingSoldier = atlas.findRegion("Suedois");
		viKing = atlas.findRegion("Roi");
	}

	public void render() {
		spriteBatch.begin();
		this.drawGrid();
		this.drawBlocks();
		spriteBatch.end();
	}

	private void drawGrid(){
		for(int i = 0; i<board.xBoard;i++){
			font.draw(spriteBatch, alphabet[i]+"", board.offsetX+(Block.SIZE*i)+(Block.SIZE/2)-5, 
					board.offsetY+(board.yBoard*Block.SIZE)+(Block.SIZE/2));
		}
		
		for(int i = 0; i<board.yBoard;i++){
			font.draw(spriteBatch, number[i]+"", board.offsetX-board.xBoard, 
					board.offsetY+(Block.SIZE*i)+(Block.SIZE/2)+10);
		}
	}
	
	private void drawBlocks() {
		for (Block block : board.getBlocks()) {
			if(block.getState()==Block.BlockState.TRONE)
				spriteBatch.draw(throneBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.FORTERESSE)
				spriteBatch.draw(escapeBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.ROUGE)
				spriteBatch.draw(moscovitBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.ROUGEEXIT)
				spriteBatch.draw(escapeRussianBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.BLANC)
				spriteBatch.draw(normalBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getState()==Block.BlockState.BLANCEXIT)
				spriteBatch.draw(escapeBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			if(block.isSurbrillance()){
				spriteBatch.draw(throneBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			}
			
			if(block.getPawn().getType()==PawnType.MOSCOVITE)
				spriteBatch.draw(moscoPawn, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			else if(block.getPawn().getType()==PawnType.SUEDOIS)
				if(block.getPawn().getTypesuede()==TypeSuedois.KING)
					spriteBatch.draw(viKing, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
				else if(block.getPawn().getTypesuede()==TypeSuedois.PION)
					spriteBatch.draw(vikingSoldier, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
			
			//Ajout du shader plus tard
			//if(block.isSurbrillance());
				//spriteBatch.
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		if( font != null ) font.dispose();
        //if( spriteBatch != null ) spriteBatch.dispose();
        if( atlas != null ) atlas.dispose();

	}

}
