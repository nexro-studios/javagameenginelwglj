package bla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Player;
import models.TextureModel;
import models.rawModel;
import rendergame.Light;
import rendergame.Loader;
import rendergame.MasterRenderer;
import rendergame.ModelTexture;
import rendergame.OBJLoader;
import rendergame.staticShader;
import rendergame.w;
import terrains.Terrain;

public class main {
	
	
	
	public static void main(String[] args) {
		
		
		w.createDisplay();
		Loader loader = new Loader(); 
		

		staticShader shader = new staticShader();
		MasterRenderer renderer = new MasterRenderer(); 
		
		//dragon render.
		rawModel model = OBJLoader.loadObjModel("tree", loader);		
		TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("tree")));
		
		rawModel model1 = OBJLoader.loadObjModel("stall", loader);		
		TextureModel staticModel1 = new TextureModel(model1, new ModelTexture(loader.loadTexture("stallTexture")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1 );
		Entity entity1 = new Entity(staticModel1, new Vector3f(20,0,-25),0,100,0,5);
		
		
		//other render
		//rawModel model1 = OBJLoader.loadObjModel("mountain_canyon_01", loader);
		//ModelTexture texture1 = new ModelTexture(loader.loadTexture("container"));
		//TextureModel teModel = new TextureModel(model1,texture1);
		//Entity ent = new Entity(teModel, new Vector3f(20,0,-50),0,0,0,1,1);
		
		//rawModel model3 = OBJLoader.loadObjModel("blaa", loader);
		//TextureModel staM = new TextureModel(model3, new ModelTexture(loader.loadTexture("red")));
		//Enitity e = new Entity(staM, new Vector3f(40, 0,-25)0,0,0,1);
		
		//Entity entity1 = new Entity(texturedModel, new Vector3f(2,0,-5), 0,0,0,1,2);
		//Entity entity2 = new Entity(texturedModel, new Vector3f(-2,0,-5), 0,0,0,1,-1);
		
		
		Camera camera = new Camera(1f,1f,0);
		Light light = new Light(new Vector3f(30,2000,30), new Vector3f(1,1,1));
		
		List<Entity> alldrag = new ArrayList<Entity>();
		Random random = new Random();
		
		
		for(int i = 0; i < 1000; i++) {
			float x = random.nextFloat() * -100 + 50;
			float y = random.nextFloat();
			float z = random.nextFloat() * 300;
			alldrag.add(new Entity(staticModel, new Vector3f(x,y,z), 0, 0, 0f, 2f));
			
		}
		
//		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain3 = new Terrain(2,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain4 = new Terrain(-2,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain5 = new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		
		Terrain terrian = new Terrain(0,0, loader, new ModelTexture(loader.loadTexture("n")));
		Player player = new Player(staticModel1, new Vector3f(0,4,30),0,0,0,1);
		while(!Display.isCloseRequested()) {
			
//			for(int i = 0; i < 2; i++) {
//				Terrain terrain = new Terrain(random.nextInt(),random.nextInt(),loader, new ModelTexture(loader.loadTexture("grass")));
//				renderer.processTerrain(terrain);
//				
//			}
			renderer.processTerrain(terrian);
			
			
			//game logic
			
			//entity.increasePosition(0, 0, 0);
			//ent.increasePosition(0, 0, 0);
			camera.move();
			//entity.increaseRotation(0, 1.2f, 0);
			renderer.render(light, camera);
			player.move();
			renderer.processEntity(player);
			//renderer.render(ent, shader);
			//renderer.render(entity1, shader);
			//renderer.render(entity, shader);
			//renderer.render(entity1, shader);
			//renderer.render(entity2, shader);
			for(Entity cude : alldrag) {
				renderer.processEntity(cude);
				//cude.increaseRotation(0, 1.2f, 0);
			}
			//renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			//renderer.processTerrain(terrain3);
			//renderer.processTerrain(terrain4);
			//renderer.processTerrain(terrain5);
			
			
			//Game loop 
			//render game
			
			w.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		w.closeDisplay();
	}
	
}
