module Java

  class Classpath
    require 'pathname'
    require 'rbconfig'

    def initialize(root_dir)
      @root = root_dir
      @locations = []
      self
    end

    def <<(paths)
      @locations = (@locations << Dir[@root + '/' + paths]).flatten
      self
    end

    def definition
      @locations.map {|path| File.native_path(path)}.join(self.separator)

    end

    def separator
     Config::CONFIG['host_os'] =~ /mswin|mingw/ ? ';' : ':'
    end

  end

end
