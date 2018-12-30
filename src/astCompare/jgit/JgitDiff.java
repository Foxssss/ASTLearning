package astCompare.jgit;

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

import astCompare.Main;
import astCompare.ZhangMain;

public class JgitDiff {
	private static Git git;
	private static String URL = "D:/java_workplace/k-9/.git";
	private static List<DiffEntry> diffs;

	public static void main(String[] args) throws IOException {
		String[] revision = getLastestVersion();
		Set<String> filePath = diffMethod(revision[0], revision[1]);
		StringBuilder newTree = new StringBuilder("R( ");
		for (String path : filePath) {
			path = "D:/java_workplace/k-9" + path;
			newTree.append(Main.getStringByJava(path)).append(" ");
			break;
		}
		newTree.append(" )");
		System.out.println("new tree str ok");
		try {
			rollBackPreRevision(diffs, revision[0], "temp");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("roll back ok");
		StringBuilder oldTree = new StringBuilder("R( ");
		for (String path : filePath) {
			path = "D:/java_workplace/k-9" + path;
			oldTree.append(Main.getStringByJava(path)).append(" ");
			break;
		}
		oldTree.append(" )");
		System.out.println("old tree str ok");
		System.out.println(ZhangMain.compair(oldTree.toString(), newTree.toString()));
	}

	private static String[] getLastestVersion() {
		String[] revision = new String[2];
		File gitDir = new File(URL);
		if (git == null) {
			try {
				git = Git.open(gitDir);
				Iterable<RevCommit> gitlog = git.log().call();
				int i = 0;
				for (RevCommit revCommit : gitlog) {
					revision[i++] = revCommit.getName();// 版本号
					if (i == 2)
						break;
					// revCommit.getAuthorIdent().getName();
					// revCommit.getAuthorIdent().getEmailAddress();
					// revCommit.getAuthorIdent().getWhen();
					// System.out.println(version);
				}
			} catch (IOException | GitAPIException e) {
				// TODO 自动生成的 catch 块
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
					if (line.startsWith("--- a")) {
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
			throw new Exception("没有需要回滚的文件");
		}
		List<String> files = new ArrayList<String>();
		// 取出需要回滚的文件，新增的文件不回滚
		for (DiffEntry diffEntry : diffEntries) {
			if (diffEntry.getChangeType() == ChangeType.DELETE) {
				continue;
			} else {
				files.add(diffEntry.getNewPath());
			}
		}
		if (files.size() == 0) {
			throw new Exception("没有需要回滚的文件");
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
		// 重新提交一次
		CommitCommand commitCmd = git.commit();
		for (String file : files) {
			commitCmd.setOnly(file);
		}
		commitCmd.setCommitter("yonge", "654166020@qq.com").setMessage(remark)
				.call();
		return true;
	}

}
