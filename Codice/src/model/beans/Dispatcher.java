package model.beans;

/*
 * Il Bean contiene un intero che specifica il tipo di percorso che ha svolto l'utente
 * in modo che le pagine a lui restituite siano quelle corrette
 * */
public class Dispatcher {
	private int num;
	
	public Dispatcher(int n) {
		this.num = n;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
