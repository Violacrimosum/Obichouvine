package s6.prog6.obichouvine.controllers;

import s6.prog6.obichouvine.models.Block;
import s6.prog6.obichouvine.models.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameRenderer {

	private TextureRegion normalBlock;
	private TextureRegion escapeBlock;
	private TextureRegion throneBlock;
	
	private SpriteBatch spriteBatch;
	
	private TextureRegion moscoPawn;
	private TextureRegion vikingSoldier;
	private TextureRegion viKing;
	
	private int ppuX, ppuY;
	
	private Board board;
	
	public GameRenderer(Board b){
		this.board = b;
		spriteBatch = new SpriteBatch();
		loadTextures();
		
		ppuX = 1;
		ppuY = 1;
	}
	
	public void loadTextures(){
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		normalBlock = atlas.findRegion("Standard-case");
		escapeBlock = atlas.findRegion("Escape-case");
		throneBlock = atlas.findRegion("Throne-case");
		moscoPawn = atlas.findRegion("Moscovit");
		vikingSoldier = atlas.findRegion("Suedois");
		viKing = atlas.findRegion("Roi");
	}
	
	public void render() {
		spriteBatch.begin();
		this.drawBlocks();
		spriteBatch.end();
	}
	
	private void drawBlocks() {
		for (Block block : board.getBlocks()) {
			spriteBatch.draw(normalBlock, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}
	
}
