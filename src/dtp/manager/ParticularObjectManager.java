package dtp.manager;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dtp.gameobject.ParticularObject;
import dtp.state.GameWorld;

public class ParticularObjectManager {
    
    protected List<ParticularObject> particularObjects;
    private GameWorld gameWorld;
    
    public ParticularObjectManager(GameWorld gameWorld) {
		
		particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
		this.gameWorld = gameWorld;
    }
    
    public GameWorld getGameWorld() {
		return this.gameWorld;
    }
    
    public void addObject(ParticularObject particularObject) {  // them 1 object vao
		
		synchronized (particularObjects) {
			
			particularObjects.add(particularObject);
		}
    }
    
    public void RemoveObject(ParticularObject particularObject) {   // xoa 1 particular 
		synchronized (particularObjects) {
			
			for(int id = 0; id < particularObjects.size(); id++) {
				
				ParticularObject object = particularObjects.get(id);
				if(object == particularObject) {
					particularObjects.remove(id);
				}
			}
		}
    }
    
    public ParticularObject getCollisionWithEnemyObject(ParticularObject object) {  // lay ra 1 object quai va cham voi nhan vat
		synchronized (particularObjects) {
			for(int id = 0; id < particularObjects.size(); id++) {
				
				ParticularObject objectInList = particularObjects.get(id);
				
				if(object.getTeamType() != objectInList.getTeamType() &&
					object.getBoundForCollisionWithEnemy().intersects(objectInList.getBoundForCollisionWithEnemy())) {
					return objectInList;
				}
			}
		}
		
		return null;
    }
    
    public ParticularObject getCollisionWithBullet(ParticularObject object) {   // lay ra 1 object dan va cham voi nhan vat
		synchronized (particularObjects) {
			for(int i = 0; i < particularObjects.size(); i++) {
				ParticularObject bulletObject = particularObjects.get(i);
				if(object.getTeamType() != bulletObject.getTeamType() &&
						object.getBoundForCollisionWithEnemy().intersects(bulletObject.getBoundForCollisionWithEnemy())) {
						return bulletObject;
				}
			}
		}
		return null;
    }
    
    public void UpdateObjects() {
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);
                
                if (!object.isObjectOutOfCameraView()) object.Update();
                if (object.getState() == ParticularObject.DEATH) {
					particularObjects.remove(id);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {   // ve object 
		
		synchronized (particularObjects) {
			
			for(ParticularObject object : particularObjects) {
				if(!object.isObjectOutOfCameraView()) object.draw(g2);
			}
		}
	}
}