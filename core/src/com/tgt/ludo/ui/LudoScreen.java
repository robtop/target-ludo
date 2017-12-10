package com.tgt.ludo.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.tgt.ludo.board.Board;

public class LudoScreen {

	public PerspectiveCamera cam;


	
	public Environment environment;
	public CameraInputController camController;
	public Shader shader;
	public RenderContext renderContext;
	Renderable renderable;

	private float i = 0;
	protected GL20 gl20 = null;
	private Board board;
	private BoardRenderer boardRenderer;


	public void create() {

		gl20 = Gdx.app.getGraphics().getGL20();
		gl20.glEnable(GL20.GL_TEXTURE_2D);
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .8f, .8f, .8f, 1f));

		// 3d Camera setup
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(-30f, 30f, -5f);
		cam.lookAt(100, -50, 0);
		cam.update();

		// allow to move the camera with the mouse
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		board = new Board();
		board.setup();
		 boardRenderer = new BoardRenderer(board, renderContext, cam, environment);

	}

	public void render() {
		camController.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		i = i + deltaTime * 200;
		if (i >= 512) {
			i = 0;
		}

		boardRenderer.renderSquares();
	}

	public void dispose() {

	}

}
