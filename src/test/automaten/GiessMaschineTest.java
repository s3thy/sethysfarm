package automaten;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GiessMaschineTest
{
   List<Pflanze> maisTestFeld = new ArrayList();
   List<Pflanze> weizenTestFeld = new ArrayList();

   @Before
   public void setData()
   {
      maisTestFeld.add(new Mais(5.5));
      maisTestFeld.add(new Mais(5.5));
      maisTestFeld.add(new Mais(5.5));

      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
      weizenTestFeld.add(new Mais(5.5));
   }


   @Test
   public void arbeitenTest()
   {
      new GiessMaschine().arbeiten(maisTestFeld);
      for( Pflanze maiskolben : maisTestFeld )
      {
         assertTrue(maiskolben.getHoehe() > 5.5);
         assertFalse(maiskolben.getHoehe() < 5.5);
      }

      new GiessMaschine().arbeiten(weizenTestFeld);
      for( Pflanze weizen : weizenTestFeld )
      {
         assertTrue(weizen.getHoehe() > 5.5);
         assertFalse(weizen.getHoehe() < 5.5);
      }

   }

   @After
   public void after() throws Exception
   {
      maisTestFeld = null;
      weizenTestFeld = null;
      assertNull(maisTestFeld);
      assertNull(weizenTestFeld);
   }
}
