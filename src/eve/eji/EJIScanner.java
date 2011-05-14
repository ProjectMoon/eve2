package eve.eji;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class EJIScanner {
	private List<String> packages = new ArrayList<String>();
	
	public EJIScanner() {
		
	}
	
	public void addPackage(String packageName) {
		packages.add(packageName);
	}
	
	public void scan() {
		FilterBuilder fb = new FilterBuilder();
		Set<URL> pkgUrls = new HashSet<URL>();
		
		for (String pkg : packages) {
			fb.include(FilterBuilder.prefix(pkg));
			pkgUrls.addAll(ClasspathHelper.getUrlsForPackagePrefix(pkg));
		}
		
		ConfigurationBuilder cb =
			new ConfigurationBuilder()
			.filterInputsBy(fb)
			.setUrls(pkgUrls)
			.setScanners(new TypeAnnotationsScanner().filterResultsBy(fb));
		
		Reflections reflections = new Reflections(cb);
		
		//Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TestAnnotation.class);
		//System.out.println(annotated);
	}
	
	public static void main(String[] args) {
		EJIScanner scanner = new EJIScanner();
		scanner.addPackage("eve.eji");
		scanner.addPackage("eve.core");
		scanner.scan();
	}
}
