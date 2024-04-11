package org.cteichert.server.utils;

import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ReflectionHelper {
	private ReflectionHelper() {
	}

	public static List<Class<?>> getClasses(Class<?> clazzForClassloaderContet, String packageName)
			throws IOException, URISyntaxException, ClassNotFoundException {
		List<String> names = getClassNamesFromPackage(clazzForClassloaderContet, packageName);
		List<Class<?>> classes = new LinkedList<>();
		for (String name : names) {
			classes.add(Class.forName(name.replace('/', '.')));
		}

		return classes;
	}

	public static List<Class<?>> getClasses(String packageName)
			throws IOException, URISyntaxException, ClassNotFoundException {
		return getClasses(ReflectionHelper.class, packageName);
	}

	public static List<Class<?>> getClassesByAnnotation(Annotation annotation) {
		return getClassesByAnnotation("org.cteichert.server", annotation);
	}

	public static List<Class<?>> getClassesByAnnotation(String prefix, Annotation annotation) {
		Reflections reflections = new Reflections(prefix);
		return new ArrayList<>(reflections.getTypesAnnotatedWith(annotation));
	}

	public static <T> List<Class<? extends T>> getClassesByType(Class<T> classType) {
		return getClassesByType("org.cteichert.server", classType);
	}

	public static <T> List<Class<? extends T>> getClassesByType(String prefix, Class<T> classType) {
		Reflections reflections = new Reflections(prefix);
		return new ArrayList<>(reflections.getSubTypesOf(classType));
	}

	@SuppressWarnings("resource")
	public static List<String> getClassNamesFromPackage(Class<?> clazzForClassloaderContet, String packageName) throws IOException, URISyntaxException {
		ClassLoader classLoader = clazzForClassloaderContet.getClassLoader();
		URL packageURL;
		List<String> names = new LinkedList<>();

		packageName = packageName.replace(".", "/");
		packageURL = classLoader.getResource(packageName);

		if (packageURL.getProtocol().equals("jar")) {
			String jarFileName;
			JarFile jf;
			Enumeration<JarEntry> jarEntries;

			jarFileName = URLDecoder.decode(packageURL.getFile(), StandardCharsets.UTF_8);
			jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
			jf = new JarFile(jarFileName);
			jarEntries = jf.entries();
			while (jarEntries.hasMoreElements()) {
				String entryName = jarEntries.nextElement().getName();

				if (entryName.contains("$")) {
					continue;
				}
					
				if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
					entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
					String[] s = entryName.split("/");
					names.add(packageName + s[0] + "." + s[1]);
				}
			}
		} else {
			URI uri = new URI(packageURL.toString());
			File folder = new File(uri.getPath());
			File[] contenuti = folder.listFiles();
			for (File actual : contenuti) {
				String entryName = actual.getName();
				
				if (entryName.contains("$")) {
					continue;
				}
				
				entryName = entryName.substring(0, entryName.lastIndexOf('.'));
				names.add(packageName + "." + entryName);
			}
		}
		return names;
	}
}