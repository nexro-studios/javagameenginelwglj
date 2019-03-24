package rendergame;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.TextureModel;
import models.rawModel;
import toolBox.Maths;

public class renderer {
	
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;
	
	
	public renderer(staticShader shader) {
		createProjectionMatrix(); 
		shader.start(); 
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	public void prepare() {
		GL11.glClearColor(0,0.3f,0.0f,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(Entity entity, staticShader shader) {
		TextureModel model = entity.getModel();
		rawModel rawmodel = model.getRawModel();
		GL30.glBindVertexArray(rawmodel.getVaoID()); 
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosistion(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		
		
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawmodel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		
		projectionMatrix = new Matrix4f(); 
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
				 
		
		
 	}
}










