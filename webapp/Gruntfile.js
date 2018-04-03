
'use strict';


module.exports = function (grunt) {

  require('time-grunt')(grunt);

  // Automatically load required Grunt tasks
  require('jit-grunt')(grunt, {
    express : 'grunt-express-server',
    useminPrepare: 'grunt-usemin',
    ngtemplates: 'grunt-angular-templates',
    cdnify: 'grunt-google-cdn',
    nwjs: 'grunt-nw-builder',
    shell:'grunt-shell'
  });

  // Configurable paths for the application
  var appConfig = {
    app: require('./bower.json').appPath || 'src',
    server : 'server',
    data : 'data',
    build : 'build',
    dist: 'dist'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({


    pkg: grunt.file.readJSON('package.json'),

    server : 'server',

    meta: {
      banner: '/**\n' +
      ' * <%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>\n' +
      ' * <%= pkg.homepage %>\n' +
      ' *\n' +
      ' * Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author %>\n' +
      ' * Licensed <%= pkg.licenses.type %> <<%= pkg.licenses.url %>>\n' +
      ' */\n'
    },

    // Project settings
    yeoman: appConfig,

    // Empties folders to start fresh
    clean: {
      build: {
        files: [
          {
            dot: true,
            src: [
              '<%= yeoman.build %>/{,*/}*',
              '.tmp',
              '<%= yeoman.dist %>/{,*/}*'
            ]
          }
        ]
      },
      server: '.tmp'
    },

    /**
     * `grunt-contrib-stylus` handles our STYLUS compilation and uglification automatically.
     * Only our `main.styl` file is included in compilation; all other files
     * must be imported from this file.
     */
    stylus: {
      build: {
        files: {
          '<%= yeoman.app %>/styles/main.css': '<%= yeoman.app %>/**/{,*/}*.styl'
        },
        options: {
          cleancss: false,
          compress: false,
          banner: '<%= meta.banner %>'
        }
      },
      compile: {
        files: {
          '<%= yeoman.app %>/styles/main.css': '<%= yeoman.app %>/**/{,*/}*.styl'
        },
        options: {
          cleancss: false,
          compress: false,
          banner: '<%= meta.banner %>'

        }
      }
    },

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      bower: {
        files: ['bower.json'],
        tasks: ['wiredep']
      },
      //js: {
      //  files: ['<%= yeoman.app %>/modules/{,*/}*.js', '<%= yeoman.app %>/scripts/{,*/}*.js', '<%= yeoman.app %>/common/{,*/}*.js'],
      //  tasks: ['newer:jshint:all', 'newer:jscs:all'],
      //  options: {
      //    livereload: '<%= connect.options.livereload %>'
      //  }
      //},
      //jsTest: {
      //  files: ['test/spec/{,*/}*.js'],
      //  tasks: ['newer:jshint:test', 'newer:jscs:test', 'karma']
      //},
      styles: {
        files: ['<%= yeoman.app %>/styles/{,*/}*.css'],
        tasks: ['newer:copy:styles', 'autoprefixer']
      },
      stylus: {
        files: ['<%= yeoman.app %>/**/{,*/}*.styl'],
        tasks: ['stylus:compile'],
        option: {
          livereload: true
        }
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      express: {
        files: [
          '<%= server %>/ftp/{,*/}*.js',
          '<%= server %>/route/{,*/}*.js',
          '<%= server %>/*.js'
        ],
        task: ['express:dev'],
        options: {
          spawn: false
        }
      },
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= yeoman.app %>/**/{,*/}*.html',
          '<%= yeoman.app %>/modules/{,*/}*.js',
          '<%= yeoman.app %>/common/{,*/}*.js',
          '.tmp/styles/{,*/}*.css',
          '<%= yeoman.app %>/assets/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
        ]
      }
    },

    // The actual grunt server settings
    connect: {
      options: {
        port: 9000,
        // Change this to '0.0.0.0' to access the server from outside.
        hostname: 'localhost',
        livereload: 35729
      },
      livereload: {
        options: {
          open: true,
          middleware: function (connect) {
            return [
              connect.static('.tmp'),
              connect().use(
                'styles',
                connect.static('styles')
              ),
              connect.static(appConfig.app)
            ];
          }
        }
      },
      test: {
        options: {
          port: 9001,
          middleware: function (connect) {
            return [
              connect.static('.tmp'),
              connect.static('test'),
              connect().use(
                '/bower_components',
                connect.static('./bower_components')
              ),
              connect.static(appConfig.app)
            ];
          }
        }
      },
      dist: {
        options: {
          open: true,
          base: '<%= yeoman.dist %>'
        }
      }
    },

    // Make sure there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '.jshintrc',
        reporter: require('jshint-stylish')
      },
      all: {
        src: [
          'Gruntfile.js',
          '<%= yeoman.app %>/modules/{,*/}*.js',
          '<%= yeoman.app %>/scripts/{,*/}*.js'
        ]
      },
      test: {
        options: {
          jshintrc: 'test/.jshintrc'
        },
        src: ['test/spec/{,*/}*.js']
      }
    },

    // Add vendor prefixed styles
    autoprefixer: {
      options: {
        browsers: ['last 1 version']
      },
      dist: {
        files: [
          {
            expand: true,
            cwd: '.tmp/styles/',
            src: '{,*/}*.css',
            dest: '.tmp/styles/'
          }
        ]
      }
    },

    // Make sure code styles are up to par
    jscs: {
      options: {
        config: '.jscsrc',
        verbose: true
      },
      all: {
        src: [
          'Gruntfile.js',
          '<%= yeoman.app %>/scripts/{,*/}*.js'
        ]
      },
      test: {
        src: ['test/spec/{,*/}*.js']
      }
    },


    // Add vendor prefixed styles
    postcss: {
      options: {
        processors: [
          require('autoprefixer-core')({browsers: ['last 1 version']})
        ]
      },
      server: {
        options: {
          map: true
        },
        files: [{
          expand: true,
          cwd: '.tmp/styles/',
          src: '{,*/}*.css',
          dest: '.tmp/styles/'
        }]
      },
      dist: {
        files: [
          {
            expand: true,
            cwd: '.tmp/styles/',
            src: '{,*/}*.css',
            dest: '.tmp/styles/'
          }
        ]
      }
    },

    // Automatically inject Bower components into the app
    wiredep: {
      app: {
        src: ['<%= yeoman.app %>/index.html'],
        ignorePath: /\.\.\//
      },
      test: {
        devDependencies: true,
        src: '<%= karma.unit.configFile %>',
        ignorePath: /\.\.\//,
        fileTypes: {
          js: {
            block: /(([\s\t]*)\/{2}\s*?bower:\s*?(\S*))(\n|\r|.)*?(\/{2}\s*endbower)/gi,
            detect: {
              js: /'(.*\.js)'/gi
            },
            replace: {
              js: '\'{{filePath}}\','
            }
          }
        }
      }
    },

    // Renames files for browser caching purposes
    filerev: {
      dist: {
        src: [
          '<%= yeoman.dist %>/scripts/{,*/}*.js',
          '<%= yeoman.dist %>/styles/{,*/}*.css',
          '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
          '<%= yeoman.dist %>/styles/fonts/*'
        ]
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      html: '<%= yeoman.app %>/index.html',
      options: {
        dest: '<%= yeoman.dist %>',
        flow: {
          html: {
            steps: {
              js: ['concat', 'uglifyjs'],
              css: ['cssmin']
            },
            post: {}
          }
        }
      }
    },

    // Performs rewrites based on filerev and the useminPrepare configuration
    usemin: {
      html: ['<%= yeoman.dist %>/src/**/{,*/}*.html'],
      //css: ['<%= yeoman.dist %>/styles/{,*/}*.css'],
      js: ['<%= yeoman.dist %>/src/common/**/{,*/}*.js',
           '<%= yeoman.dist %>/src/modules/**/{,*/}*.js',
           '<%= yeoman.dist %>/server/*.js',
           '<%= yeoman.dist %>/server/ftp/*.js',
           '<%= yeoman.dist %>/server/route/*.js',
          ],
      options: {
        assetsDirs: [
          '<%= yeoman.dist %>',
          '<%= yeoman.dist %>/assets',
          '<%= yeoman.dist %>/images',
          //'<%= yeoman.dist %>/styles'
        ],
        patterns: {
          js: [[/(images\/[^''""]*\.(png|jpg|jpeg|gif|webp|svg))/g, 'Replacing references to images']]
        }
      }
    },

    // The following *-min tasks will produce minified files in the dist folder
    // By default, your `index.html`'s <!-- Usemin block --> will take care of
    // minification. These next options are pre-configured if you do not wish
    // to use the Usemin blocks.
    // concat: {
    // dist: {}
    // },
    //cssmin: {
    //  dist: {
    //    options: {
    //      report: 'min'
    //    },
    //    files: {
    //      expand: true,
    //     cwd: '<%= yeoman.app %>',
    //      src: ['styles/{,*/}*.css'],
    //      dest: '<%= yeoman.dist %>/styles'
    //    }
    //  }
   // },

    uglify: {
      options: {
        banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'//banner
      },
      dist: {
        options: {
          beautify : true,
          mangle : true,
          preserveComments: false,
          compress : {
            drop_console: true
          },
          report: "min"// false()gzip
        },
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= yeoman.app %>',
          src: [
            'common/**/{,*/}*.js',
            'modules/**/{,*/}*.js',
          ],
          dest: '<%= yeoman.dist %>/src'
        },
        {
          expand: true,
          dot: true,
          cwd: '<%= yeoman.server %>',
          dest: '<%= yeoman.dist %>/server',
          src: [
            //'**/{,*/}*.*'
            '*.js',
            'ftp/{,*/}*.js',
            'route/{,*/}*.js'
          ]
        }]
      }
    },

    imagemin: {
      dist: {
        options: {
          optimizationLevel: 7,
          pngquant: true
        },
        files: [
          {
            expand: true,
            cwd: '<%= yeoman.app %>/assets/images',
            src: '{,*/}*.{png,jpg,jpeg,gif}',
            dest: '<%= yeoman.dist %>/assets/images'
          }
        ]
      }
    },

    htmlmin: {
      dist: {
        options: {
          conservativeCollapse: true,
          removeComments: true,
          removeCommentsFromCDATA: true,
          collapseWhitespace: true,
          collapseBooleanAttributes: true,
          removeAttributeQuotes: true,
          removeRedundantAttributes: true,
          useShortDoctype: true,
          removeEmptyAttributes: true,
          removeOptionalTags: true
        },
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>',
          src: [
            '*.html',
            'common/**/{,*/}*.html',
            'modules/**/{,*/}*.html',
          ],
          dest: '<%= yeoman.dist %>/src'
        }]
      }
    },

    ngtemplates: {
      dist: {
        options: {
          module: 'webappApp',
          htmlmin: '<%= htmlmin.dist.options %>',
          usemin: 'scripts/scripts.js'
        },
        cwd: '<%= yeoman.app %>',
        src: 'views/{,*/}*.html',
        dest: '.tmp/templateCache.js'
      }
    },

    // ng-annotate tries to make the code safe for minification automatically
    // by using the Angular long form for dependency injection.
    ngAnnotate: {
      dist: {
        files: [{
          expand: true,
          cwd: '.tmp/concat/scripts',
          src: '*.js',
          dest: '.tmp/concat/scripts'
        }]
      }
    },

    // Replace Google CDN references
    cdnify: {
      dist: {
        html: ['<%= yeoman.dist %>/*.html']
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      build:  {
        files:[
          {
            expand: true,
            dot: true,
            dest: '<%= yeoman.build %>/nff/win32',
            src: [
              '<%= yeoman.data %>/**/*',
              '!<%= yeoman.data %>/log/**/*'
            ]
          }
        ]
      },
      dist: {
        files: [
          {
            expand: true,
            dot: true,
            cwd: '<%= yeoman.app %>',
            dest: '<%= yeoman.dist %>/src',
            src: [
              '**/{,*/}*.*',
              '!**/{,*/}*.styl'
            ]
          },
          {
            expand: true,
            dot: true,
            cwd: '<%= yeoman.server %>',
            dest: '<%= yeoman.dist %>/server',
            src: [
              '**/{,*/}*.*'
            ]
          },
          {
            expand: true,
            dot: true,
            cwd: '<%= yeoman.data %>',
            dest: '<%= yeoman.dist %>/data',
            src: [
              '**/{,*/}*.*',
              '!log/{,*/}*.*'
            ]
          }
        ]
      },
      styles: {
        expand: true,
        cwd: '<%= yeoman.app %>/styles',
        dest: '.tmp/styles/',
        src: '{,*/}*.css'
      }
    },

    compress: {
      build: {
        options: {
          archive: '<%= yeoman.build %>/nff_<%= grunt.template.today("yyyymmddHHMM") %>.zip'
        },
        files: [
          {
            expand: true,
            cwd: '<%= yeoman.build %>',
            src: [
              '**/*'
            ],
            dest: '/'
          },
        ]
      }
    },

    express: {

      options: {

      },
      dev: {
        options: {
          script : 'server/server.js'
        }
      }
    },


    // Run some tasks in parallel to speed up the build process
    concurrent: {
      server: [
        'copy:styles'
      ],
      test: [
        'copy:styles'
      ],
      dist: [
        'copy:styles',
        'imagemin',
        'svgmin'
      ]
    },

    nwjs: {
      options: {
        version: '0.14.7',
        platforms: ['win32'],
        buildDir: '<%= yeoman.build %>',
        flavor: 'sdk',
        zip: false
      },
      src: [
        '<%= yeoman.dist %>/server/**/*',
        '<%= yeoman.dist %>/src/**/*'
      ]
    },

    // Karma test settings
    karma: {
      unit: {
        configFile: 'test/karma.conf.js',
        singleRun: true
      }
    },
    //versions

    buildNumber: grunt.option('buildNumber'),

    buildTimestamp: grunt.option('buildTimestamp'),

    version: grunt.option('gitVersion')

  });


  grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
    if (target === 'dist') {
      return grunt.task.run(['build', 'connect:dist:keepalive']);
    }

    grunt.task.run([
      'clean:server',
      'wiredep',
      'concurrent:server',
      'postcss:server',
      'connect:livereload',
      'watch'
    ]);
  });

  grunt.registerTask('server', 'Start a express server', function (target) {

    grunt.task.run([
      'clean:server',
      'wiredep',
      'concurrent:server',
      'postcss:server',
      'express:dev',
      'watch'
    ]);
  });

  grunt.registerTask('test', [
    'clean:server',
    'wiredep',
    'concurrent:test',
    'postcss',
    'connect:test',
    'karma'
  ]);

  grunt.registerTask('build', [
    'clean:build',
    //'wiredep',
    //'useminPrepare',
    'stylus:build',
    //'concurrent:dist',
    //'postcss',
    //  'ngtemplates',

    //'concat',
    //'ngAnnotate',
    'copy:dist',
    //'cdnify',
    //'cssmin',
    'uglify',
    //'filerev',
    'imagemin',
    'htmlmin',
    'usemin',
    //'createVersion',
    //'nwjs',
    'copy:build',
    'compress:build'
  ]);

  grunt.registerTask('nw',[
    'nwjs'
  ]);

  grunt.registerTask('default', [
    'newer:jshint',
    'newer:jscs',
    'test',
    'build'
  ]);

  //grunt.registerTask('getVersion', ['shell:getVersionFile']);
  //
  //grunt.registerTask('write_targz1', ['shell:addFileToZip:nff_mtk.tar.gz']);
  //
  //grunt.registerTask('write_targz2', ['shell:addFileToZip:nff_mtkg120ea.tar.gz']);

  //grunt.registerTask('createVersion',[
  //  'getVersion',
  //  'write_targz1',
  //  'write_targz2'
  //]);
};
