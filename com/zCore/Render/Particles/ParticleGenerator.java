package com.zCore.Render.Particles;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class ParticleGenerator
{
  public static int anzahl;
  public static int breite;
  public static int h�he;
  public ArrayList<Particle> particles = new ArrayList();
  private Random random = new Random();
  private Timer timer = new Timer();
  
  public ParticleGenerator(int anzahl, int breite, int h�he)
  {
    anzahl = anzahl;
    
    breite = breite;
    
    h�he = h�he;
    for (int i = 0; i < anzahl; i++) {
      this.particles.add(new Particle(this.random.nextInt(breite), this.random.nextInt(h�he)));
    }
  }
  
  public void drawParticles()
  {
    for (Particle p : this.particles) {
      p.draw();
    }
  }
}
