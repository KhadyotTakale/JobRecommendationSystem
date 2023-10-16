import java.util.*;

class User {
    String name;
    String qualifications;
    String skills;

    public User(String name, String qualifications, String skills) {
        this.name = name;
        this.qualifications = qualifications.toLowerCase();
        this.skills = skills.toLowerCase();
    }
}

class Job {
    String title;
    String requiredQualifications;
    String requiredSkills;

    public Job(String title, String requiredQualifications, String requiredSkills) {
        this.title = title;
        this.requiredQualifications = requiredQualifications.toLowerCase();
        this.requiredSkills = requiredSkills.toLowerCase();
    }
}

class Graph {
    private final Map<User, List<Job>> userJobMap = new HashMap<>();

    public void addInteraction(User user, Job job) {
        userJobMap.computeIfAbsent(user, k -> new ArrayList<>()).add(job);
    }

    public List<Job> recommendJobs(User user) {
        List<Job> recommendedJobs = new ArrayList<>();
        Queue<User> queue = new LinkedList<>();
        Set<User> visited = new HashSet<>();

        queue.offer(user);
        visited.add(user);

        while (!queue.isEmpty()) {
            User currentUser = queue.poll();
            for (Job job : userJobMap.get(currentUser)) {
                if (qualificationsMatch(currentUser.qualifications, job.requiredQualifications) &&
                        skillsMatch(currentUser.skills, job.requiredSkills)) {
                    recommendedJobs.add(job);
                }
            }

            for (User nextUser : userJobMap.keySet()) {
                if (!visited.contains(nextUser)) {
                    visited.add(nextUser);
                    queue.offer(nextUser);
                }
            }
        }

        return recommendedJobs;
    }

    private boolean qualificationsMatch(String userQualifications, String requiredQualifications) {
        String[] requiredQualificationsArray = requiredQualifications.split(",");
        for (String req : requiredQualificationsArray) {
            if (!userQualifications.contains(req)) {
                return false;
            }
        }
        return true;
    }

    private boolean skillsMatch(String userSkills, String requiredSkills) {
        String[] requiredSkillsArray = requiredSkills.split(",");
        int matchedSkillsCount = 0;
        for (String req : requiredSkillsArray) {
            String[] userSkillsArray = userSkills.split(",");
            for (String user : userSkillsArray) {
                if (user.contains(req)) {
                    matchedSkillsCount++;
                    break;
                }
            }
        }
        return matchedSkillsCount >= 2;
    }
}

public class RecommendationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your qualifications: ");
        String qualifications = scanner.nextLine();
        System.out.println("Enter your skills: ");
        String skills = scanner.nextLine();

        User user = new User(name, qualifications, skills);

        Job job1 = new Job("Software Developer", "Computer Science", "Java,Python,C++,C,Ruby");
        Job job2 = new Job("Data Analyst", "Statistics", "SQL,Excel,DataEntry");
        Job job3 = new Job("Product Manager", "MBA", "Leadership,Communication,Mathematics");
        Job job4 = new Job("Marketing Specialist", "Marketing", "Marketing Strategy,Mathematics,Selling");
        Job job5 = new Job("Web Developer", "Computer Science", "HTML,CSS,JavaScript");
        Job job6 = new Job("App Developer", "Computer Science", "Java,Dart,Flutter");
        Job job7 = new Job("Game Developer", "Computer Science", "C++,Unity,Maths");
        Job job8 = new Job("Bhai Kya kar raha hai tu?", "Entc", "C++,Aurdiuno,Maths,IC");

        Graph graph = new Graph();
        graph.addInteraction(user, job1);
        graph.addInteraction(user, job2);
        graph.addInteraction(user, job3);
        graph.addInteraction(user, job4);
        graph.addInteraction(user, job5);
        graph.addInteraction(user, job6);
        graph.addInteraction(user, job7);
        graph.addInteraction(user, job8);

        List<Job> recommendations = graph.recommendJobs(user);
        System.out.println("Recommended Jobs for " + user.name + ":");
        if (recommendations.isEmpty()) {
            System.out.println("No suitable jobs found.");
        } else {
            System.out.println("Suitable job options:");
            for (Job job : recommendations) {
                System.out.println(job.title);
            }
        }
    }
}
