package model.beans;

/*
 * Il Bean memorizza le informazioni (totale vaccinazioni, numero prime, seconde e uniche dosi)
 *  relative al report semplice
 * */
public class ReportS {
	private int totVaccini, numPD, numSD, numUD;
	
	public ReportS(int tot, int PD, int SD, int UD) {
		this.totVaccini = tot;
		this.numPD = PD;
		this.numSD = SD;
		this.numUD = UD;
	}

	public int getTotVaccini() {
		return totVaccini;
	}

	public void setTotVaccini(int totVaccini) {
		this.totVaccini = totVaccini;
	}

	public int getNumPD() {
		return numPD;
	}

	public void setNumPD(int numPD) {
		this.numPD = numPD;
	}

	public int getNumSD() {
		return numSD;
	}

	public void setNumSD(int numSD) {
		this.numSD = numSD;
	}

	public int getNumUD() {
		return numUD;
	}

	public void setNumUD(int numUD) {
		this.numUD = numUD;
	}
}
