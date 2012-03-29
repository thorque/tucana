/**
 * 
 */
package org.tucana.importer.tools

/**
 * @author kamann
 *
 */
class DownloadCategory {
 
	def static leftShift(File a_file, URL a_url) {
		def input
		def output

		try {
			input = a_url.openStream()
			output = new BufferedOutputStream(new FileOutputStream(a_file))
			output << input
		}
		finally {
			input?.close()
			output?.close()
		}
	}

}
