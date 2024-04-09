package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class keylistener extends KeyAdapter{	//키 리스너
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("키 누름");
		
		if (e.getKeyCode() == 27&&BitTetris.Gamestate==1) {	//esc키 누르면
			BitTetris.Th[0].interrupt();
			BitTetris.Th[1].interrupt();
		}
	}
}


//class Mykey extends KeyAdapter
//{
//	@Override
//	public void keyPressed(KeyEvent e) 
//	{
//		int keyCord = e.getKeyCode();
//		if (e.getKeyCode()==27)
//			 System.out.println("ddddd");
//		switch(keyCord)
//		{
//		case KeyEvent.VK_UP : 
//			la.setLocation(la.getX(),la.getY()-10);
//			break;
//		case KeyEvent.VK_DOWN : 
//			la.setLocation(la.getX(),la.getY()+10);
//			break;
//		case KeyEvent.VK_RIGHT : 
//			la.setLocation(la.getX()+10,la.getY());
//			break;
//		case KeyEvent.VK_LEFT : 
//			la.setLocation(la.getX()-10,la.getY());
//			break;	
//		case 27 : 
//			System.exit(0);
//			break;	
//			default: System.out.println("The key Pressed was: " + e.getKeyCode());
//		}
//		
//	}
//	
//}