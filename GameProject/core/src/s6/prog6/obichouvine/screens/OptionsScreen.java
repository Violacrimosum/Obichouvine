package s6.prog6.obichouvine.screens;

import java.util.Locale;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.MusicManager.ObiMusic;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * A simple options screen.
 */
public class OptionsScreen extends AbstractScreen {
    private Label volumeValue;

    public OptionsScreen(ObichouvineGame game)
    {
        super(game);
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.defaults().spaceBottom(30);
        table.columnDefaults(0).padRight(20);
        table.add("Options").colspan(3).padRight(0);

        // create the labels widgets
        final TextField pseudoTextField = new TextField(game.getPreferencesManager().getPseudo(), getSkin());
        pseudoTextField.setTextFieldListener(new TextFieldListener() {
			
			@Override
			public void keyTyped(TextField textField, char c) {
				// TODO Auto-generated method stub
				String changedPseudo = pseudoTextField.getText();
				System.out.println("test pseudo "+changedPseudo);
                game.getPreferencesManager().setPseudo(changedPseudo);
			}
		});
        table.row();
        table.add("Pseudonyme Moscovites: ");
        table.add(pseudoTextField).colspan(2).left().fillX();
        
        final TextField pseudo2TextField = new TextField(game.getPreferencesManager().getPseudoP2(), getSkin());
        pseudo2TextField.setTextFieldListener(new TextFieldListener() {
			
			@Override
			public void keyTyped(TextField textField, char c) {
				// TODO Auto-generated method stub
				String changedPseudo = pseudo2TextField.getText();
				System.out.println("test pseudo 2 "+changedPseudo);
                game.getPreferencesManager().setPseudoP2(changedPseudo);
			}
		});
        table.row();
        table.add("Pseudonyme Vikings: ");
        table.add(pseudo2TextField).colspan(2).left().fillX();
        
        final CheckBox soundEffectsCheckbox = new CheckBox("", getSkin());
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled());
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled(enabled);
                game.getSoundManager().setEnabled(enabled);
                game.getSoundManager().play(ObiSound.CLICK);
            }
        } );
        table.row();
        table.add("Effets sonores : ");
        table.add(soundEffectsCheckbox).colspan(2).left();

        final CheckBox musicCheckbox = new CheckBox("", getSkin());
        musicCheckbox.setChecked(game.getPreferencesManager().isMusicEnabled());
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferencesManager().setMusicEnabled(enabled);
                game.getMusicManager().setEnabled(enabled);
                game.getSoundManager().play(ObiSound.CLICK);

                // if the music is now enabled, start playing the menu music
                if(enabled) game.getMusicManager().play(ObiMusic.MENU);
            }
        } );
        table.row();
        table.add("Musique : ");
        table.add(musicCheckbox).colspan(2).left();

        // range is [0.0,1.0]; step is 0.1f
        Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, getSkin());
        volumeSlider.setValue(game.getPreferencesManager().getVolume());
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                float value = ( (Slider) actor ).getValue();
                game.getPreferencesManager().setVolume( value );
                game.getMusicManager().setVolume( value );
                game.getSoundManager().setVolume( value );
                updateVolumeLabel();
            }
        } );

        // create the volume label
        volumeValue = new Label("", getSkin());
        updateVolumeLabel();

        // add the volume row
        table.row();
        table.add("Volume : ");
        table.add(volumeSlider);
        table.add(volumeValue).width(40);

        // register the back button
        TextButton backButton = new TextButton("Retour au menu principal", getSkin());
        backButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( ObiSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        });
        table.row();
        table.add(backButton).height(60).colspan(3).padRight(0).fillX();
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
        float volume = ( game.getPreferencesManager().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
}