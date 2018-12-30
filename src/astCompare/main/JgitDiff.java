package astCompare.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import astCompare.kit.StringDistanceUtils;
import astCompare.kit.TreeToString;

public class JgitDiff {
	private static String project_path = "D:/github/k-9";
	private static Git git;
	private static String URL = project_path + "/.git";
	private static List<DiffEntry> diffs;

	public static void main(String[] args) throws IOException {
		String[] revision = getLastestVersion();
		Set<String> filePath;
		int lastVersion = 1;
		do {
			filePath = diffMethod(revision[lastVersion - 1], revision[lastVersion]);
			lastVersion ++;
		} while (filePath.isEmpty() && lastVersion < revision.length);
		
		// get new version ast string
		System.out.println("===============new tree str begin===============");
		String[] newTrees = new String[filePath.size()];
		int count = 0;
		for (String path : filePath) {
			path = project_path + path;
			newTrees[count] = TreeToString.getStringByJava(path);
			System.out.println(path + ": (string length)" + newTrees[count].length());
			System.out.println(count++);
		}
		System.out.println("===============new tree str ok===============");
		
		// roll back
		try {
			rollBackPreRevision(diffs, revision[lastVersion - 1], "temp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("roll back ok");
		
		// get old version ast string
		System.out.println("===============old tree str begin===============");
		String[] oldTrees = new String[filePath.size()];
		count = 0;
		for (String path : filePath) {
			path = project_path + path;
			oldTrees[count] = TreeToString.getStringByJava(path);
			System.out.println(path + ": (string length)" + oldTrees[count].length());
			System.out.println(count++);
		}
		System.out.println("===============new tree str ok===============");
		
		int edit_distance_sum = 0, max_substr_sum = 0;
		for (int i = 0; i < newTrees.length; ++i) {
			edit_distance_sum += StringDistanceUtils.editDistance(oldTrees[i], newTrees[i]);
			max_substr_sum += StringDistanceUtils.maxSubStringLength(oldTrees[i], newTrees[i]);
		}
		System.out.println("The similarity between the latest two version of project: \n"
				+ "\t" + project_path + "\n"
				+ "sum of edit distance: " + edit_distance_sum + "\n"
				+ "sum of max substring length: " + max_substr_sum + "\n");
	}

	private static String[] getLastestVersion() {
		// get latest 10 version
		String[] revision = new String[10];
		File gitDir = new File(URL);
		if (git == null) {
			try {
				git = Git.open(gitDir);
				Iterable<RevCommit> gitlog = git.log().call();
				int i = 0;
				for (RevCommit revCommit : gitlog) {
					revision[i++] = revCommit.getName();
					if (i == revision.length)
						break;
					// revCommit.getAuthorIdent().getName();
					// revCommit.getAuthorIdent().getEmailAddress();
					// revCommit.getAuthorIdent().getWhen();
					// System.out.println(version);
				}
			} catch (IOException | GitAPIException e) {
				e.printStackTrace();
			}
		}
		return revision;
	}

	private static Set<String> diffMethod(String Child, String Parent) {
		Set<String> filePath = new HashSet<>();
		Repository repository = git.getRepository();
		ObjectReader reader = repository.newObjectReader();
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();

		try {
			ObjectId old = repository.resolve(Child + "^{tree}");
			ObjectId head = repository.resolve(Parent + "^{tree}");
			oldTreeIter.reset(reader, old);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);
			diffs = git.diff().setNewTree(newTreeIter)
					.setOldTree(oldTreeIter).call();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DiffFormatter df = new DiffFormatter(out);
			df.setRepository(git.getRepository());

			for (DiffEntry diffEntry : diffs) {
				df.format(diffEntry);
				String diffText = out.toString("UTF-8");
				for (String line : diffText.split("\n")) {
					// the changed file should be a java file
					if (line.startsWith("--- a") && line.endsWith(".java")) {
						filePath.add(line.substring("--- a".length()));
					}
				}
				// out.reset();
			}
			df.close();
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public static boolean rollBackPreRevision(List<DiffEntry> diffEntries,
			String revision, String remark) throws Exception {

		if (diffEntries == null || diffEntries.size() == 0) {
			throw new Exception("没有�?要回滚的文件");
		}
		List<String> files = new ArrayList<String>();
		// 取出要回滚的文件，新增的文件不回
		for (DiffEntry diffEntry : diffEntries) {
			if (diffEntry.getChangeType() == ChangeType.DELETE) {
				continue;
			} else {
				files.add(diffEntry.getNewPath());
			}
		}
		if (files.size() == 0) {
			throw new Exception("没有�?要回滚的文件");
		}
		// checkout操作会丢失工作区的数据，暂存区和工作区的数据会恢复到指定（revision）的版本内容
		CheckoutCommand checkoutCmd = git.checkout();
		for (String file : files) {
			checkoutCmd.addPath(file);
		}
		// 加了“^”表示指定版本的前一个版本，如果没有上一版本，在命令行中会报错，例如：error: pathspec '4.vm' did not
		// match any file(s) known to git.
		checkoutCmd.setStartPoint(revision + "^");
		checkoutCmd.call();
		// 重新提交
		CommitCommand commitCmd = git.commit();
		for (String file : files) {
			commitCmd.setOnly(file);
		}
		commitCmd.setCommitter("temp", "temp@temp.com").setMessage(remark)
				.call();
		return true;
	}

}
