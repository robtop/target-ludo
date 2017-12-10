package com.tgt.ludo.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.tgt.ludo.LudoGameState;
import com.tgt.ludo.board.Board;

public class LudoScreen {

	public PerspectiveCamera cam;
	// for 2D
	OrthographicCamera guiCam;
	public Environment environment;
	public CameraInputController camController;
	public Shader shader;
	public RenderContext renderContext;
	Renderable renderable;

	private float i = 0;
	protected GL20 gl20 = null;
	
	private BoardRenderer boardRenderer;
	protected Vector3 touchPoint;
	LudoGameState ludoGameState;
	
	public void create(LudoGameState ludoGameState) {
        this.ludoGameState = ludoGameState;
        
		gl20 = Gdx.app.getGraphics().getGL20();
		gl20.glEnable(GL20.GL_TEXTURE_2D);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .8f, .8f, .8f, 1f));

		// 3d Camera setup
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(-30f, 30f, -5f);
		cam.lookAt(100, -50, 0);
		cam.update();

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		
		// allow to move the camera with the mouse
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		boardRenderer = new BoardRenderer(ludoGameState.getBoard(), renderContext, cam, environment);
		touchPoint = new Vector3();
	}

	public void render() {
		camController.update();
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		i = i + deltaTime * 200;
		if (i >= 512) {
			i = 0;
		}

		boardRenderer.renderSquares();
	}

	public void dispose() {
		boardRenderer.dispose();
	}

	public void update() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		}
  //any screen inputs?
	}
}
