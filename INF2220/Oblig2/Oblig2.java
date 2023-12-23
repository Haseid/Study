import java.util.*;
import java.io.*;

class Oblig2 {
	public static void main(String[] args) {
		if (args.length == 1) {
			// starter programmet
			new Run(args[0]);
		} else {
			// viser riktig bruk
			System.out.println("\nUsage: \"java Oblig2 inputfile.txt\"\n");
		}
	}
}

class Run {
	ArrayList<Task> taskList = new ArrayList<Task>();
	int projectETA = 0;

	// konstruktør
	public Run(String fil) {
		innlesnng(fil);
		setEdges();
		analyse();
		findEarliestStart();
		findLatestStart();
		printInfo();
		printSchedule();

	}

	// metode som leser inn fil
	public void innlesnng(String fil) {
		try {

			// attributter for innlesning
			Scanner read = new Scanner(new File(fil));
			int taskCnt = read.nextInt();
			Task tmpTask;
			int tmpID;

			read.nextLine();
			read.nextLine();

			// leser inn en task
			for (int i = 0; i < taskCnt; i++) {	
				tmpTask = new Task(
					read.nextInt(),
					read.next(),
					read.nextInt(),
					read.nextInt(),
					this
				);

				// leser inn avhenighetene til task'en
				while(1<2) {
					tmpID = read.nextInt();
					if (tmpID != 0) {
						tmpTask.dependencies.add(tmpID);
						tmpTask.dependencies2.add(tmpID);
					} else {
						break;
					}
				}
				taskList.add(tmpTask);
			}
		} catch (IOException e) {
			System.out.println("Error while reading inputfile");
		}
	}

	// metode som setter edges til å peke på riktige steder
	public void setEdges() {
		for (int i = 0; i < taskList.size(); i++) {
			for (int taskID : taskList.get(i).dependencies) {
				// går inn i en task ser på avhengighet
				// 		går i avhenighetene sine edges
				//			setter task'en utenfor inn i edges
				taskList.get((taskID-1)).addEdge(taskList.get(i));
			}
		}
	}

	// metode som starter opp søk etter loop i planleggingen
	public void analyse() {
		Task first;
		ArrayList<Task> loop = new ArrayList<Task>();

		// finner første task og starter å lete etter loop
		for (int i = 0; i < taskList.size(); i++) {
			first = taskList.get(i);
			if (first.isIndependent()) {
				// metoden returenerer et løp av peker som går i en loop
				loop = first.circular(loop);
			}
		}

		// skriver ut løpet som er loop og terminerer programmet
		if (loop != null) {
			for (int i = (loop.indexOf(loop.get(loop.size()-1))-1); i>=0; i--) {
				loop.remove(i);
			}
			for (int i = 0; i<loop.size(); i++) {
				System.out.println(loop.get(i).id + " " + loop.get(i).name);
			}
			System.exit(0);
		}
	}

	// finner earliestStart til alle task'ene
	public void findEarliestStart() {
		int timer = 0;
		Task tmpTask;

		while(!allDone()) {
			for (int i = 0; i<taskList.size(); i++) {
				tmpTask = taskList.get(i);
				if ((tmpTask.isIndependent()) && (!tmpTask.isDone) && (!tmpTask.started)) {
					tmpTask.startTask(timer);
				}
			}
			// plusser på tiden
			timer++;
			projectETA = timer;

			// kjører en tidsenhet
			executeTime();
		}
	}

	// kjører gjennom tidsenhet
	public void executeTime() {
		Task tmpTask;

		for (int i = 0; i<taskList.size(); i++) {
			tmpTask = taskList.get(i);
			if (tmpTask.started) {
				// kjører gjennom task'en
				tmpTask.timer--;
				if (tmpTask.timer == 0) {
					// setter at tasken er ferdig
					tmpTask.isDone = true;
					removeDependencies(tmpTask);
				}
			}
		}
	}

	// metode frigjør alle task'ene som er avhengig av Task t
	public void removeDependencies(Task t) {
		for (int i = 0; i<taskList.size(); i++) {
			taskList.get(i).taskIsDone(t.id);
		}
	}

	// metode som sjekker om alle task'ene er ferdige
	public boolean allDone() {
		for (int i = 0; i<taskList.size(); i++) {
			if (!taskList.get(i).isDone) {
				return false;
			}
		}
		return true;
	}

	// metdoe som finner latestStart
	public void findLatestStart() {
		Task tmp;
		Task first = null;

		// oppretter avhengighetene på nytt, etter å ha fjernet de fra findEarliestStart metoden
		for (int i = 0; i < taskList.size(); i++) {
			tmp = taskList.get(i);
			tmp.resetTask();
		}
		// finner første task
		for (int i = 0; i < taskList.size(); i++) {
			tmp = taskList.get(i);
			if (tmp.isIndependent()) {
				first = taskList.get(i);
			}
		}
		// starter den rekursive metoden på den første task'en
		first.latestStart(projectETA);
	}

	// metode som sier ifra til alle task'ene at tiden har gått så langt at de evt er ferdige
	public void notifyAll(int tid) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).earliestStart<tid) {
				// setter task'en til å være ferdig
				taskList.get(i).notifyEdge(taskList.get(i).id);
			}
		}
	}

	// mtode som printer planen for programmet
	public void printSchedule() {
		// attributter
		int timer = -1;
        int tasksDone = 0;
        int staff = 0;
        boolean event = false;

        System.out.println("-------- Project schedule --------\n");
        // kjører gjennom task'ene
        while (tasksDone < taskList.size()) {
            timer++;
            event = false;
            // går gjennom alle task'ene
            for (Task t : taskList) {
            	// ser om en task kan starte
                if (t.earliestStart == timer) {
                    if (!event) {
                        System.out.println("Time: " + timer);
                        event = true;
                    }
                    System.out.println("Starting: " + t.id);
                    staff = staff + t.manpower;
                }
                // ser om en task er ferdig
                if ((t.earliestStart + t.time) == timer) {
                    if (!event) {
                        System.out.println("Time: " + timer);
                        event = true;
                    }
                    System.out.println("Finished: " + t.id);
                    tasksDone++;
                    staff = staff - t.manpower;
                }
            }
            // skriver ut antall manpower om det er skjedd noe
            if (event)
                System.out.println("Current Staff: " + staff + "\n");
        }
	}

	// metode som printer ut info om alle task
	public void printInfo() {
		System.out.println("-------- Task info --------\n");

		for (int i = 0; i < taskList.size(); i++) {
			// skriver ut basic info
			System.out.println("Task ID: " + taskList.get(i).id);
			System.out.println("Name: " + taskList.get(i).name);
			System.out.println("Time usage: " + taskList.get(i).time);
			System.out.println("Manpower: " + taskList.get(i).manpower);
			System.out.println("Slack: " + (taskList.get(i).latestStart - taskList.get(i).earliestStart));
			System.out.println("Latest start: " + taskList.get(i).latestStart);
			System.out.println("Edges: ");

			// skirver ut alle edges
			for (int j = 0; j<taskList.get(i).outEdges.length(); j++) {
				System.out.println("Task " + taskList.get(i).outEdges.edges.get(j).id + ", " + taskList.get(i).outEdges.edges.get(j).name);
			}
			System.out.println("");
		}
		// skirver ut kritiske task'er
		System.out.println("-------- Critical tasks --------\n");
		for (int i = 0; i < taskList.size(); i++) {
			if ((taskList.get(i).latestStart - taskList.get(i).earliestStart) == 0) {
				System.out.println("Task " + taskList.get(i).id + ", " + taskList.get(i).name);
			}
		}
		System.out.println("");
	}
}

// en klasse som inneholder alle edge'ene til en task
// ville heller ha foretrukket en arrayList i task objektet
// men oppgaven foreslo en egen klasse...
class Edge {
	ArrayList<Task> edges = new ArrayList<Task>();

	public void addEdge(Task t) {
		edges.add(t);
	}

	public int length() {
		return edges.size();
	}

	// metode som finner neste task som har høyest tid og er uavhengig
	public Task maxTask() {
		int storst = 0;
		int index = 0;
		for (int i = 0; i<edges.size(); i++) {
			if ((edges.get(i).time > storst) && (edges.get(i).isIndependent())) {
				storst = edges.get(i).time;
				index = i;
			}
		}

		return edges.get(index);
	}
}

// klasse for en oppgave / task
class Task {

	// attributter for task
	int id, time, timer, manpower;
	int earliestStart, latestStart, slacktime;
	int cntPredecessors;
	boolean started = false, isDone = false;
	String name;
	Edge outEdges;
	Run run;
	ArrayList<Integer> dependencies = new ArrayList<Integer>();
	ArrayList<Integer> dependencies2 = new ArrayList<Integer>();

	// konstruktør
	public Task(int id, String name, int time, int manpower, Run run) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.timer = time;
		this.manpower = manpower;
		this.outEdges = new Edge();
		this.run = run;
	}

	public void addEdge(Task t) {
		outEdges.addEdge(t);
	}

	public boolean isIndependent() {
		return dependencies.isEmpty();
	}

	// metode som fikser gamle pekere
	public void resetTask() {
		dependencies = dependencies2;
		timer = time;
		isDone = false;
	}

	// metode som frigjør denne(this) task'en fra en gitt avhengighet
	public void taskIsDone(int id) {
		dependencies.remove(new Integer(id));
	}

	// rekursiv metode som finner latestStart, full tid i parameter
	public void latestStart(int eta) {
		// sjekker om den ikke har noen edges
		if (outEdges.edges.isEmpty()) {
			// sjekker om den er gjort fra før
			if (!isDone) {
				// setter latestStart
				latestStart = eta - time;
				// setter at den er ferdig for å ungå overlagring
				isDone = true;
				return;
			}
			return;
		}

		// sier ifra til sin edge at denne(this) task'en er gjort
		notifyEdge(id);
		// Oppdaterer alle task'ene at denne(this) task'en er gjort
		run.notifyAll(earliestStart + time);
		// gjør dette for at den ikke skal velge sin neste task som
		// bare har høyest tid, men også et det er mulig å ta den er uavhengig

		// tar vare på neste task (som sansynelig vis er kritisk)
		Task nextTask = outEdges.maxTask();
		for (int i = 0; i<outEdges.length(); i++) {
			// kaller metoden på de andre kantene
			outEdges.edges.get(i).latestStart(eta);
		}

		// sjekker om den er gjort fra før
		if (!isDone) {
			// setter latestStart
			latestStart = nextTask.latestStart - time;
			// setter at den er ferdig for å ungå overlagring
			isDone = true;
		}
	}

	// metode som sier ifra til alle kantene at de er frigjort fra denne(this) task'en
	public void notifyEdge(int id) {
		if (!outEdges.edges.isEmpty()) {
			for (int i = 0; i<outEdges.length(); i++) {
				outEdges.edges.get(i).taskIsDone(id);
			}
		}
	}

	// metode som går gjennom alle task'ene, tar vare på dem en etter en
	// om den finner en loop så returnerer den en array med loopen i
	public ArrayList<Task> circular(ArrayList<Task> list) {
		ArrayList<Task> tmp;

		// sjekker om denne(this) task'en alleredet ligger i lista
		if(list.contains(this)) {
			System.out.println("Loop was found in the schedule: ");
			// legger til denne(this) task'en og returnerer lista
			list.add(this);
			return list;
		} else {
			// legger da til denne(this) i lista
			list.add(this);
			for (int i = 0; i<outEdges.length(); i++) {
				// sender lista vider til sine kanter
				tmp = outEdges.edges.get(i).circular(list);
				// om den får igjen noe som ikke er null returnerer den lista videre
				if (tmp != null) {
					return list;
				}
			}
			// ellers så sletter den seg selv fra lista og metoden returnerer null
			list.remove(this);
		}
		return null;
	}

	// metode som setter earlistStart og at task'en har startet
	public void startTask(int earliestStart) {
		this.earliestStart = earliestStart;
		started = true;
	}
}